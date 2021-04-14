package ru.bmstu.privateservice.utils;

import org.springframework.stereotype.Service;
import ru.bmstu.privateservice.dto.DoctorResponse;
import ru.bmstu.privateservice.model.Doctor;

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
        return dto;
    }
}