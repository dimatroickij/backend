package ru.bmstu.appointment.commonmodel.utils;

import org.springframework.stereotype.Service;
import ru.bmstu.appointment.commonmodel.dto.DoctorResponse;
import ru.bmstu.appointment.commonmodel.model.Doctor;

@Service
public class DoctorMapping {
    // Из Entity в Dto
    public DoctorResponse mapToDoctor(Doctor doctor){
        DoctorResponse dto = new DoctorResponse();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setMiddleName(doctor.getMiddleName());
        dto.setSurName(doctor.getSurName());
        dto.setSpeciality(doctor.getSpeciality());
        dto.setCabinet(doctor.getCabinet());
        return dto;
    }
}
