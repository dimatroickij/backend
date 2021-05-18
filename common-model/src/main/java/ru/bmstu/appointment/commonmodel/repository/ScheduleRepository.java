package ru.bmstu.appointment.commonmodel.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.appointment.commonmodel.model.Appointment;
import ru.bmstu.appointment.commonmodel.model.Doctor;
import ru.bmstu.appointment.commonmodel.model.Schedule;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    List<Schedule> findByDoctorAndDateBetween(@NotNull Doctor doctor, @NotNull Date date, @NotNull Date date2, Sort sort);

    List<Schedule> findByDateBetween(@NotNull Date date, @NotNull Date date2, Sort sort);

    List<Schedule> findByDateBefore(@NotNull Date date, Sort sort);

    List<Schedule> findByDateAfter(@NotNull Date date, Sort sort);

    List<Schedule> findByDoctorAndDateBefore(@NotNull Doctor doctor, @NotNull Date date, Sort sort);

    List<Schedule> findByDoctorAndDateAfter(@NotNull Doctor doctor, @NotNull Date date, Sort sort);

    List<Schedule> findByDoctor(@NotNull Doctor doctor, Sort sort);

    default List<Schedule> findByAppointmentsEmpty(List<Schedule> schedules) {
        return schedules.stream().filter(item -> item.getAppointments().stream().noneMatch(Appointment::getIsActive))
                .collect(Collectors.toList());
    }

}
