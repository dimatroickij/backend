package ru.bmstu.appointment.publicservice.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.appointment.commonmodel.dto.AppointmentUpdateRequest;
import ru.bmstu.appointment.commonmodel.dto.ExceptionResponse;
import ru.bmstu.appointment.commonmodel.model.Appointment;
import ru.bmstu.appointment.commonmodel.dto.AppointmentRequest;
import ru.bmstu.appointment.commonmodel.dto.AppointmentResponse;
import ru.bmstu.appointment.commonmodel.model.Pacient;
import ru.bmstu.appointment.commonmodel.model.Schedule;
import ru.bmstu.appointment.commonmodel.repository.AppointmentRepository;
import ru.bmstu.appointment.commonmodel.repository.PacientRepository;
import ru.bmstu.appointment.commonmodel.repository.ScheduleRepository;
import ru.bmstu.appointment.commonmodel.utils.AppointmentMapping;
import ru.bmstu.appointment.publicservice.exception.ActiveAppointmentException;
import ru.bmstu.appointment.publicservice.exception.InvalidAppointmentException;
import ru.bmstu.appointment.publicservice.exception.NotFoundPacientException;
import ru.bmstu.appointment.publicservice.component.NotificationProducer;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointment")
@Tag(name = "Appointment", description = "Операции с записями пациентов к врачу")
public class AppointmentController {

    @Value("${send-email}")
    private boolean sendEmail;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PacientRepository pacientRepository;

    @Autowired
    private AppointmentMapping appointmentMapping;

    @Autowired
    private NotificationProducer notificationProducer;

    @SecurityRequirements
    @Operation(summary = "Список записей в зависимости от заданных фильтров",
            description = "Для получения списка записей на определенный день заполните startDate и endDate этой датой")
    @GetMapping("/all")
    public List<AppointmentResponse> all(@RequestParam(required = false) UUID idDoctor,
                                         @RequestParam(required = false)
                                         @Schema(type = "string",
                                                 pattern = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012]).\\d{4}$",
                                                 example = "dd.MM.yyyy") String startDate,
                                         @RequestParam(required = false)
                                         @Schema(type = "string",
                                                 pattern = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012]).\\d{4}$",
                                                 example = "dd.MM.yyyy") String endDate) throws ParseException {

        if (idDoctor == null && startDate == null && endDate == null)
            return appointmentRepository.findByIsActiveTrue(Sort.by("schedule_date", "schedule_startTime")).stream()
                    .map(appointmentMapping::mapToAppointment).collect(Collectors.toList());

        if (idDoctor == null && startDate == null)
            return appointmentRepository.findByIsActiveTrueAndScheduleDateBefore(
                    new SimpleDateFormat("dd.MM.yyyy").parse(endDate),
                    Sort.by("schedule_date", "schedule_startTime")).stream().map(appointmentMapping::mapToAppointment)
                    .collect(Collectors.toList());

        if (idDoctor == null && endDate == null)
            return appointmentRepository.findByIsActiveTrueAndScheduleDateAfter(
                    new SimpleDateFormat("dd.MM.yyyy").parse(startDate),
                    Sort.by("schedule_date", "schedule_startTime")).stream().map(appointmentMapping::mapToAppointment)
                    .collect(Collectors.toList());

        if (idDoctor == null)
            return appointmentRepository.findByIsActiveTrueAndScheduleDateBetween(
                    new SimpleDateFormat("dd.MM.yyyy").parse(startDate),
                    new SimpleDateFormat("dd.MM.yyyy").parse(endDate),
                    Sort.by("schedule_date", "schedule_startTime")).stream().map(appointmentMapping::mapToAppointment)
                    .collect(Collectors.toList());

        if (startDate == null && endDate == null)
            return appointmentRepository.findByIsActiveTrueAndScheduleDoctorId(idDoctor,
                    Sort.by("schedule_date", "schedule_startTime")).stream().map(appointmentMapping::mapToAppointment)
                    .collect(Collectors.toList());

        if (startDate == null)
            return appointmentRepository.findByIsActiveTrueAndScheduleDoctorIdAndScheduleDateBefore(idDoctor,
                    new SimpleDateFormat("dd.MM.yyyy").parse(endDate), Sort.by("schedule_date", "schedule_startTime")).
                    stream().map(appointmentMapping::mapToAppointment).collect(Collectors.toList());

        if (endDate == null)
            return appointmentRepository.findByIsActiveTrueAndScheduleDoctorIdAndScheduleDateAfter(idDoctor,
                    new SimpleDateFormat("dd.MM.yyyy").parse(startDate), Sort.by("schedule_date", "schedule_startTime")).
                    stream().map(appointmentMapping::mapToAppointment).collect(Collectors.toList());

        return appointmentRepository.findByIsActiveTrueAndScheduleDoctorIdAndScheduleDateBetween(idDoctor,
                new SimpleDateFormat("dd.MM.yyyy").parse(startDate),
                new SimpleDateFormat("dd.MM.yyyy").parse(endDate), Sort.by("schedule_date", "schedule_startTime")).
                stream().map(appointmentMapping::mapToAppointment).collect(Collectors.toList());
    }

    @Operation(summary = "Просмотр данных конкретной записи")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = AppointmentResponse.class))})})
    @GetMapping("/{id}")
    public AppointmentResponse get(@PathVariable UUID id) {
        if (appointmentRepository.existsById(id)) {
            Appointment appointment = appointmentRepository.getOne(id);
            return appointmentMapping.mapToAppointment(appointment);
        }
        return null;
    }

    @Operation(summary = "Генерация талона записи")
    @GetMapping("/{id}.pdf")
    public ResponseEntity<InputStreamResource> print(@PathVariable UUID id) throws DocumentException, IOException {
        if (appointmentRepository.existsById(id)) {
            Appointment appointment = appointmentRepository.getOne(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));

            return new ResponseEntity<InputStreamResource>(
                    new InputStreamResource(getPDF(new AppointmentMapping().mapToAppointment(appointment))), headers,
                    HttpStatus.OK);
        }
        return null;
    }

    @Operation(summary = "Создание записи на приём к врачу")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Запись создана",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Пациент или расписание не найдено",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Запись уже занята",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})})
    @PostMapping("/add")
    public AppointmentResponse add(@RequestBody @Valid AppointmentRequest request) throws ParseException,
            ActiveAppointmentException, NotFoundPacientException {

        Appointment appointment = new Appointment();
        Schedule schedule = scheduleRepository.getOne(request.getSchedule());
        List<Appointment> activeAppointment = schedule.getAppointments().stream().filter(Appointment::getIsActive).
                collect(Collectors.toList());

        if (!activeAppointment.isEmpty())
            throw new ActiveAppointmentException("Запись на " + activeAppointment.get(0).getSchedule().getDate() +
                    " " + activeAppointment.get(0).getSchedule().getStartTime() + " уже занята. " +
                    "Выберите другое время.");

        appointment.setSchedule(scheduleRepository.getOne(request.getSchedule()));
        appointment.setDateRecord(new Date());

        if (pacientRepository.existsBySurNameAndNameAndMiddleNameAndBirthDayAndPolicy(request.getSurName(),
                request.getName(), request.getMiddleName(),
                new SimpleDateFormat("dd.MM.yyyy").parse(request.getBirthDay()), request.getPolicy())) {

            Pacient pacient = pacientRepository.findBySurNameAndNameAndMiddleNameAndBirthDayAndPolicy(
                    request.getSurName(),
                    request.getName(), request.getMiddleName(),
                    new SimpleDateFormat("dd.MM.yyyy").parse(request.getBirthDay()),
                    request.getPolicy());
            appointment.setPacient(pacient);
//            appointment.setEmail(request.getEmail());
            appointmentRepository.save(appointment);

            if (sendEmail)
                notificationProducer.sendMessageWithCallback(request.getEmail() + "###" +
                        appointment.getId().toString());
            return appointmentMapping.mapToAppointment(appointment);
        }
        throw new NotFoundPacientException("Пациент не найден. Убедитесь, что введены корретные данные " +
                "или прикрепитесь к поликлинике");
    }

    @Operation(summary = "Изменение записи на приём к врачу")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Данные записи изменены",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentResponse.class))}),
            @ApiResponse(responseCode = "304", description = "Запись недействительна",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Расписание не найдено",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Запись уже занята",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})})
    @PutMapping("/update/{id}")
    public AppointmentResponse update(@RequestBody @Valid AppointmentUpdateRequest request, @PathVariable UUID id)
            throws NotFoundException, InvalidAppointmentException {
        if (appointmentRepository.existsById(id)) {
            Appointment lastAppointment = appointmentRepository.getOne(id);
            if (!lastAppointment.getIsActive())
                throw new InvalidAppointmentException("Запись " + lastAppointment.getId() + " отменена.");

            lastAppointment.setIsActive(false);
            appointmentRepository.save(lastAppointment);

            Appointment appointment = new Appointment();

            Schedule schedule = scheduleRepository.getOne(request.getSchedule());
            List<Appointment> activeAppointment = schedule.getAppointments().stream().filter(Appointment::getIsActive).
                    collect(Collectors.toList());

            if (!activeAppointment.isEmpty())
                throw new ActiveAppointmentException("Запись на " + activeAppointment.get(0).getSchedule().getDate() +
                        " " + activeAppointment.get(0).getSchedule().getStartTime() + " уже занята. " +
                        "Выберите другое время.");

            appointment.setPacient(lastAppointment.getPacient());
            appointment.setSchedule(scheduleRepository.getOne(request.getSchedule()));
            appointment.setDateRecord(new Date());
//            appointment.setEmail(request.getEmail());
            appointmentRepository.save(appointment);

            if (sendEmail)
                notificationProducer.sendMessageWithCallback(request.getEmail() + "###" +
                        appointment.getId().toString());
            return appointmentMapping.mapToAppointment(appointment);
        }
        throw new NotFoundException("Запись не найдена. Убедитесь, что введены корретные данные");
    }

    @Operation(summary = "Удаление записи на приём к врачу")
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable UUID id) {
        if (appointmentRepository.existsById(id)) {
            Appointment appointment = appointmentRepository.getOne(id);
            if (appointment.getIsActive()) {
                appointment.setIsActive(false);
                appointmentRepository.save(appointment);
                return "OK";
            }
            return "Запись с таким кодом уже удалена";
        }
        return null;
    }

    private InputStream getPDF(AppointmentResponse appointment) throws DocumentException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A6, 0, 0, 0, 0);
        PdfWriter.getInstance(document, byteArrayOutputStream);

        document.open();

        BaseFont bf = BaseFont.createFont("/OpenSans-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf);

        BaseFont bfBold = BaseFont.createFont("/OpenSans-Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontBold = new Font(bfBold);

        BaseFont bfExtraBold = BaseFont.createFont("/OpenSans-ExtraBold.ttf", BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED);
        Font fontExtraBold = new Font(bfExtraBold);

        // Адрес поликлиники
        Paragraph paragraph = new Paragraph("Москва, Бригадирский пер., д. 14", font);
        paragraph.setAlignment(1);
        document.add(paragraph);

        // QR код
        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(appointment.getPacient().getPolicy(),
                100, 100, null);
        Image codeQrImage = barcodeQRCode.getImage();
        codeQrImage.setAlignment(1);
        document.add(codeQrImage);

        paragraph = new Paragraph("Талон к врачу №", fontBold);
        paragraph.setAlignment(1);
        document.add(paragraph);

        // Номер записи
        paragraph = new Paragraph(String.valueOf(appointment.getId()), font);
        paragraph.setAlignment(1);
        document.add(paragraph);

        // Дата приёма
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        DateFormat dfDate = new SimpleDateFormat("dd.MM.yyyy");

        String date = appointment.getSchedule().getDate();
        String time = appointment.getSchedule().getStartTime();
        paragraph = new Paragraph(date + " " + time, font);
        paragraph.setAlignment(1);
        document.add(paragraph);

        // Специальность доктора
        paragraph = new Paragraph(appointment.getSchedule().getDoctor().getSpeciality().getName(), font);
        paragraph.setAlignment(1);
        document.add(paragraph);

        // ФИО врача
        String middleName = appointment.getSchedule().getDoctor().getMiddleName();
        String name = appointment.getSchedule().getDoctor().getName();
        String surName = appointment.getSchedule().getDoctor().getSurName();
        paragraph = new Paragraph(surName + " " + name + " " + middleName, fontExtraBold);
        paragraph.setAlignment(1);
        document.add(paragraph);

        // Номер кабинета
        paragraph = new Paragraph("Кабинет № " + appointment.getSchedule().getDoctor().getCabinet(), font);
        paragraph.setAlignment(1);
        document.add(paragraph);

        document.close();

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
