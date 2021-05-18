package ru.bmstu.appointment.commonmodel.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.appointment.commonmodel.model.Appointment;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByIsActiveTrue(Sort sort);

    List<Appointment> findByIsActiveTrueAndScheduleDoctorIdAndScheduleDateBetween(@NotEmpty UUID doctor,
                                                                                  @NotNull Date date,
                                                                                  @NotNull Date date2, Sort sort);

    List<Appointment> findByIsActiveTrueAndScheduleDateBetween(@NotNull Date date, @NotNull Date date2, Sort sort);

    List<Appointment> findByIsActiveTrueAndScheduleDateBefore(@NotNull Date schedule_date, Sort sort);

    List<Appointment> findByIsActiveTrueAndScheduleDateAfter(@NotNull Date schedule_date, Sort sort);

    List<Appointment> findByIsActiveTrueAndScheduleDoctorIdAndScheduleDateBefore(@NotEmpty UUID doctor,
                                                                                 @NotNull Date date, Sort sort);

    List<Appointment> findByIsActiveTrueAndScheduleDoctorIdAndScheduleDateAfter(@NotEmpty UUID doctor,
                                                                                @NotNull Date date, Sort sort);

    List<Appointment> findByIsActiveTrueAndScheduleDoctorId(@NotEmpty UUID schedule_doctor_id, Sort sort);


}
