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
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.appointment.commonmodel.dto.AppointmentUpdateRequest;
import ru.bmstu.appointment.commonmodel.model.Appointment;
import ru.bmstu.appointment.commonmodel.dto.AppointmentRequest;
import ru.bmstu.appointment.commonmodel.dto.AppointmentResponse;
import ru.bmstu.appointment.commonmodel.model.Pacient;
import ru.bmstu.appointment.commonmodel.repository.AppointmentRepository;
import ru.bmstu.appointment.commonmodel.repository.PacientRepository;
import ru.bmstu.appointment.commonmodel.repository.ScheduleRepository;
import ru.bmstu.appointment.commonmodel.utils.AppointmentMapping;
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
    @Operation(summary = "Список записей")
    @GetMapping("/all")
    public List<AppointmentResponse> all() {
        return appointmentRepository.findAll().stream().map(appointmentMapping::mapToAppointment).collect(Collectors.toList());
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
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
//            schema = @Schema(implementation = AppointmentResponse.class))})})
    @GetMapping("/{id}.pdf")
    public ResponseEntity<InputStreamResource> print(@PathVariable UUID id) throws DocumentException, IOException {
        if (appointmentRepository.existsById(id)) {
            Appointment appointment = appointmentRepository.getOne(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));

            ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
                    new InputStreamResource(getPDF(new AppointmentMapping().mapToAppointment(appointment))), headers, HttpStatus.OK);
            return response;
        }
        return null;
    }

    @Operation(summary = "Создание записи на приём к врачу")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Запись создана",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentResponse.class))})})
    @PostMapping("/add")
    public AppointmentResponse add(@RequestBody @Valid AppointmentRequest request) throws ParseException {
        Appointment appointment = new Appointment();
        appointment.setSchedule(scheduleRepository.getOne(request.getSchedule()));
        appointment.setDateRecord(new Date());

        if (pacientRepository.existsBySurNameAndNameAndMiddleNameAndBirthDayAndPolicy(request.getSurName(),
                request.getName(), request.getMiddleName(),
                new SimpleDateFormat("dd.MM.yyyy").parse(request.getBirthDay()), request.getPolicy())) {

            Pacient pacient = pacientRepository.findBySurNameAndNameAndMiddleNameAndBirthDayAndPolicy(request.getSurName(),
                    request.getName(), request.getMiddleName(),
                    new SimpleDateFormat("dd.MM.yyyy").parse(request.getBirthDay()),
                    request.getPolicy());
            appointment.setPacient(pacient);
            appointment.setIsActive(true);
//            appointment.setEmail(request.getEmail());
            appointmentRepository.save(appointment);
            notificationProducer.sendMessageWithCallback(request.getEmail());
            return appointmentMapping.mapToAppointment(appointment);
        }
        return null;
    }

    @Operation(summary = "Изменение записи на приём к врачу")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Данные записи изменены",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentResponse.class))})})
    @PutMapping("/update/{id}")
    public AppointmentResponse update(@RequestBody @Valid AppointmentUpdateRequest request, @PathVariable UUID id) {
        if (appointmentRepository.existsById(id)) {
            Appointment lastAppointment = appointmentRepository.getOne(id);
            lastAppointment.setIsActive(false);
            appointmentRepository.save(lastAppointment);

            Appointment appointment = new Appointment();
            appointment.setPacient(lastAppointment.getPacient());
            appointment.setSchedule(scheduleRepository.getOne(request.getSchedule()));
            appointment.setDateRecord(new Date());
            appointment.setIsActive(true);
//            appointment.setEmail(request.getEmail());
            appointmentRepository.save(appointment);
            notificationProducer.sendMessageWithCallback(request.getEmail());
            return appointmentMapping.mapToAppointment(appointment);
        }
        return null;
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

        BaseFont bfExtraBold = BaseFont.createFont("/OpenSans-ExtraBold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
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
