package com.example.msloader.service.implementation;

import com.example.msloader.model.CDR;
import com.example.msloader.service.CDRParser;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class XmlParser implements CDRParser {
    private final XmlMapper xmlMapper;
    public XmlParser() {
        xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
    }
    @Override
    public List<CDR> parse(InputStream inputStream, String filename) throws IOException {
        CDR[] cdrsArray = xmlMapper.readValue(inputStream, CDR[].class);
        List<CDR> cdrs = Arrays.asList(cdrsArray);

        // Set the filename for each CDR
        cdrs.forEach(cdr -> cdr.setFileName(filename));

        return cdrs;
    }
}
