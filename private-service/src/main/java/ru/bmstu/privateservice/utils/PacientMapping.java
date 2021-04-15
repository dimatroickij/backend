package ru.bmstu.privateservice.utils;

import org.springframework.stereotype.Service;
import ru.bmstu.privateservice.dto.PacientResponse;
import ru.bmstu.privateservice.model.Pacient;

@Service
public class PacientMapping {
    // Из Entity в Dto
    public PacientResponse mapToPacient(Pacient pacient){
        PacientResponse dto = new PacientResponse();
        dto.setId(pacient.getId());
        dto.setName(pacient.getName());
        dto.setMiddleName(pacient.getMiddleName());
        dto.setSurName(pacient.getSurName());
        dto.setBirthDay(pacient.getBirthDay());
        dto.setPolicy(pacient.getPolicy());
        //dto.setUsername(pacient.getUser().getUsername());
        return dto;
    }
}
