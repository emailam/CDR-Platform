package com.example.msbackend.service;

import com.example.msbackend.model.CDR;
import com.example.msbackend.model.CDRDTO;

import java.util.List;
import java.util.UUID;


public interface ICDRService {
    CDRDTO createCDR(CDRDTO cdrDTO);

    CDRDTO getCDRById(UUID id);

    List<CDRDTO> getAllCDRs();

    CDRDTO updateCDR(UUID id, CDRDTO cdrdto);

    void deleteCDR(UUID id);

    void saveCDRFromKafka(CDR cdr);
}
