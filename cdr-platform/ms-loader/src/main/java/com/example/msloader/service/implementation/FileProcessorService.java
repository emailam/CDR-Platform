package com.example.msloader.service.implementation;

import com.example.msloader.model.CDR;
import com.example.msloader.model.ServiceType;
import com.example.msloader.repository.CDRRepository;
import com.example.msloader.service.CDRParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class FileProcessorService {

    private final ParserFactory parserFactory;
    private final CDRRepository cdrRepository;
    private final KafkaProducerService kafkaProducerService;

    @Value("${app.cdr.source-directory}")
    private String sourceDirectory;

    @Value("${app.cdr.processed-directory}")
    private String processedDirectory;

    @Value("${app.cdr.failed-directory}")
    private String failedDirectory;


    public FileProcessorService(ParserFactory parserFactory,
                                CDRRepository cdrRepository,
                                KafkaProducerService kafkaProducerService) {
        this.parserFactory = parserFactory;
        this.cdrRepository = cdrRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Scheduled(fixedRateString = "${app.cdr.processing-interval-ms}")
    public void processFiles() {
        log.info("Starting file processing...");

        // Ensure directories exist
        createDirectoryIfNotExists(sourceDirectory);
        createDirectoryIfNotExists(processedDirectory);
        createDirectoryIfNotExists(failedDirectory);

        File dir = new File(sourceDirectory);
        File[] files = dir.listFiles();

        if (files == null || files.length == 0) {
            log.info("No files to process");
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                processFile(file);
            }
        }

        log.info("File processing completed");
    }

    @Scheduled(fixedRateString = "${app.cdr.processing-interval-ms}")
    public void generateRandomCDR() {
        CDR cdr = new CDR();
        cdr.setSource("rand1");
        cdr.setDestination("rand2");
        cdr.setStartTime(LocalDateTime.now());
        cdr.setService(ServiceType.DATA);
        cdr.setUsage(3F);
        cdr.setFileName("random.rand");

        // save to database
        cdrRepository.save(cdr);

        // produce a message to kafka
        kafkaProducerService.produce(cdr);
    }

    private void processFile(File file) {
        log.info("Processing file: {}", file.getName());

        String fileName = file.getName();
        String extension = getFileExtension(fileName);

        try {
            CDRParser parser = parserFactory.getParser(extension);

            try (FileInputStream fis = new FileInputStream(file)) {
                List<CDR> cdrs = parser.parse(fis, fileName);

                log.info("Parsed {} CDRs from file {}", cdrs.size(), fileName);

                // Save to database
                cdrRepository.saveAll(cdrs);

                // produce a message to kafka
                for (CDR cdr : cdrs) {
                    System.out.println("sending....");
                    kafkaProducerService.produce(cdr);
                }

                // Move file to processed directory
                moveFile(file, processedDirectory);

                log.info("Successfully processed file: {}", fileName);
            } catch (IOException e) {
                log.error("Error processing file: {}", fileName, e);
                moveFile(file, failedDirectory);
            }
        } catch (IllegalArgumentException e) {
            log.error("Unsupported file extension for file: {}", fileName);
            moveFile(file, failedDirectory);
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    private void moveFile(File file, String destinationDir) {
        try {
            Path sourcePath = file.toPath();
            Path targetPath = Paths.get(destinationDir, file.getName());
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Failed to move file: {}", file.getName(), e);
        }
    }

    private void createDirectoryIfNotExists(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                log.info("Created directory: {}", directory);
            } else {
                log.error("Failed to create directory: {}", directory);
            }
        }
    }
}