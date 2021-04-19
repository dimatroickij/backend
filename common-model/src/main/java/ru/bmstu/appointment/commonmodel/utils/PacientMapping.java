package ru.bmstu.appointment.commonmodel.utils;

import org.springframework.stereotype.Service;
import ru.bmstu.appointment.commonmodel.dto.PacientResponse;
import ru.bmstu.appointment.commonmodel.model.Pacient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Service
public class PacientMapping {
    // Из Entity в Dto
    public PacientResponse mapToPacient(Pacient pacient){
        PacientResponse dto = new PacientResponse();
        dto.setId(pacient.getId());
        dto.setName(pacient.getName());
        dto.setMiddleName(pacient.getMiddleName());
        dto.setSurName(pacient.getSurName());

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        dto.setBirthDay(df.format(pacient.getBirthDay()));
        dto.setPolicy(pacient.getPolicy());
        //dto.setUsername(pacient.getUser().getUsername());
        return dto;
    }
}
