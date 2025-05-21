package com.example.msloader.service.implementation;

import com.example.msloader.service.CDRParser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ParserFactory {

    private final Map<String, CDRParser> parsers = new HashMap<>();

    public ParserFactory(CSVParser csvParser, JsonParser jsonParser,
                         XmlParser xmlParser, YamlParser yamlParser) {
        parsers.put("csv", csvParser);
        parsers.put("json", jsonParser);
        parsers.put("xml", xmlParser);
        parsers.put("yaml", yamlParser);
        parsers.put("yml", yamlParser);
    }

    public CDRParser getParser(String fileExtension) {
        CDRParser parser = parsers.get(fileExtension.toLowerCase());
        if (parser == null) {
            throw new IllegalArgumentException("No parser available for extension: " + fileExtension);
        }
        return parser;
    }
}