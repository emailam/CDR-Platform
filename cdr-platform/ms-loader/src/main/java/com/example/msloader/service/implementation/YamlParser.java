package com.example.msloader.service.implementation;

import com.example.msloader.model.CDR;
import com.example.msloader.service.CDRParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class YamlParser implements CDRParser {
    private final ObjectMapper yamlMapper;

    public YamlParser() {
        yamlMapper = new ObjectMapper(new YAMLFactory());
        yamlMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<CDR> parse(InputStream inputStream, String filename) throws IOException {
        List<CDR> cdrs = yamlMapper.readValue(inputStream, new TypeReference<List<CDR>>() {});

        // Set the filename for each CDR
        cdrs.forEach(cdr -> cdr.setFileName(filename));

        return cdrs;
    }
}
