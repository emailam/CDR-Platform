package com.example.msbackend.service.implementation;

import com.example.msbackend.exception.CDRNotFound;
import com.example.msbackend.exception.InvalidCDR;
import com.example.msbackend.model.CDR;
import com.example.msbackend.model.CDRDTO;
import com.example.msbackend.model.ServiceType;
import com.example.msbackend.repository.CDRRepository;
import com.example.msbackend.service.ICDRService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class CDRService implements ICDRService {
    private final CDRRepository cdrRepository;
    private final DTOConversionService dtoConversionService;

    @Override
    public CDRDTO createCDR(CDRDTO cdrDTO) {
        CDR cdr = dtoConversionService.convertToEntity(cdrDTO);
        CDR savedCDR = cdrRepository.save(cdr);
        return dtoConversionService.convertToDTO(savedCDR);
    }

    @Override
    public CDRDTO getCDRById(UUID id) {
        CDR cdr = cdrRepository.findById(id).orElseThrow(() -> new CDRNotFound("CDR not found with id: " + id));
        return dtoConversionService.convertToDTO(cdr);
    }

    @Override
    public List<CDRDTO> getAllCDRs() {
        List<CDR> cdrList = cdrRepository.findAll();
        List<CDRDTO> cdrdtoList = new ArrayList<>();
        for (CDR cdr : cdrList) {
            cdrdtoList.add(dtoConversionService.convertToDTO(cdr));
        }
        return cdrdtoList;
    }

    @Override
    public CDRDTO updateCDR(UUID id, CDRDTO cdrdto) {
        CDR cdr = cdrRepository.findById(id).orElseThrow(() -> new CDRNotFound("CDR not found with id: " + id));


        if (cdrdto.getService() == ServiceType.SMS && cdrdto.getUsage() != 1) {
            throw new InvalidCDR("For SMS service, usage must be exactly 1");
        }

        // Update only allowed fields.
        cdr.setSource(cdrdto.getSource());
        cdr.setDestination(cdrdto.getDestination());
        cdr.setStartTime(cdrdto.getStartTime());
        cdr.setService(cdrdto.getService());
        cdr.setUsage(cdrdto.getUsage());
        cdr.setFileName(cdrdto.getFileName());

        CDR updatedCDR = cdrRepository.save(cdr);
        return dtoConversionService.convertToDTO(updatedCDR);
    }

    @Override
    public void deleteCDR(UUID id) {
        if (!cdrRepository.existsById(id)) {
            throw new CDRNotFound("CDR not found with id: " + id);
        }
        cdrRepository.deleteById(id);
    }

    @Override
    public void saveCDRFromKafka(CDR cdr) {
        if (cdr == null) {
            throw new InvalidCDR("CDR cannot be null");
        }
        cdr.setId(null); // ensure new record creation.
        cdrRepository.save(cdr);
    }
}
