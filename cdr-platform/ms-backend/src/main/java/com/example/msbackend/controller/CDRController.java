package com.example.msbackend.controller;

import com.example.msbackend.model.CDRDTO;
import com.example.msbackend.service.ICDRService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("api")
public class CDRController {
    private final ICDRService cdrService;

    @Tag(name = "post", description = "Create CDR")
    @PostMapping(path = "/cdr")
    public ResponseEntity<CDRDTO> createCDR(@Valid @RequestBody CDRDTO cdrdto) {
        CDRDTO cdr = cdrService.createCDR(cdrdto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cdr);
    }

    @Tag(name = "get", description = "GET methods of CDR APIs")
    @GetMapping("/cdr/{id}")
    public ResponseEntity<CDRDTO> getCDRById(@PathVariable("id") UUID id) {
        CDRDTO cdr = cdrService.getCDRById(id);
        return ResponseEntity.ok(cdr);
    }

    @Tag(name = "get", description = "Retrieve one CDR")
    @GetMapping("/cdrs")
    public ResponseEntity<List<CDRDTO>> getAllCDRs() {
        List<CDRDTO> cdrs = cdrService.getAllCDRs();
        return cdrs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(cdrs);
    }

    @Tag(name = "put", description = "Update a CDR")
    @PutMapping("/cdr/{id}")
    public ResponseEntity<CDRDTO> updateCDR(@PathVariable("id") UUID id, @Valid @RequestBody CDRDTO cdrdto) {
        CDRDTO updatedCDRDTO = cdrService.updateCDR(id, cdrdto);
        return ResponseEntity.ok(updatedCDRDTO);
    }

    @Tag(name = "delete", description = "Delete a CDR")
    @DeleteMapping("/cdr/{id}")
    public ResponseEntity<String> deleteCDR(@PathVariable("id") UUID id) {
        cdrService.deleteCDR(id);
        return ResponseEntity.ok("CDR deleted successfully");
    }

}
