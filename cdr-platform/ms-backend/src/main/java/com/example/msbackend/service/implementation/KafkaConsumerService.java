package com.example.msbackend.service.implementation;

import com.example.msbackend.model.CDR;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class KafkaConsumerService {
    private final CDRService cdrService;

    @KafkaListener(topics = "cdr", groupId = "group_id")
    public void consume(CDR cdr) {
        cdrService.saveCDRFromKafka(cdr);
        System.out.println("Received message: " + cdr);
    }
}
