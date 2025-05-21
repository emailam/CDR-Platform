package com.example.msloader.service.implementation;

import com.example.msloader.model.CDR;
import com.example.msloader.model.ServiceType;
import com.example.msloader.service.CDRParser;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVParser implements CDRParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public List<CDR> parse(InputStream inputStream, String filename) throws IOException {
        List<CDR> cdrs = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) {
            List<String[]> rows = csvReader.readAll();

            // Skip header row if present
            boolean skipHeader = true;

            for (String[] row : rows) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                CDR cdr = new CDR();
                cdr.setSource(row[0]);
                cdr.setDestination(row[1]);
                cdr.setStartTime(LocalDateTime.parse(row[2], FORMATTER));
                cdr.setService(ServiceType.valueOf(row[3]));
                cdr.setUsage(Float.parseFloat(row[4]));
                cdr.setFileName(filename);

                cdrs.add(cdr);
            }
        } catch (CsvException e) {
            throw new IOException("Error parsing CSV file", e);
        }

        return cdrs;
    }
}
