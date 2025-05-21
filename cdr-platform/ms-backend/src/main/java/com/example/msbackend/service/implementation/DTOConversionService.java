package com.example.msbackend.service.implementation;

import com.example.msbackend.model.CDR;
import com.example.msbackend.model.CDRDTO;
import org.springframework.stereotype.Service;

@Service
public class DTOConversionService {
    public CDRDTO convertToDTO(CDR cdr) {
        return CDRDTO.builder()
                .id(cdr.getId())
                .source(cdr.getSource())
                .destination(cdr.getDestination())
                .startTime(cdr.getStartTime())
                .service(cdr.getService())
                .usage(cdr.getUsage())
                .updatedAt(cdr.getUpdatedAt())
                .createdAt(cdr.getCreatedAt())
                .fileName(cdr.getFileName())
                .build();
    }

    public CDR convertToEntity(CDRDTO dto) {
        CDR cdr = new CDR();
        cdr.setSource(dto.getSource());
        cdr.setDestination(dto.getDestination());
        cdr.setStartTime(dto.getStartTime());
        cdr.setService(dto.getService());
        cdr.setUsage(dto.getUsage());
        cdr.setFileName(dto.getFileName());
        return cdr;
    }
}
