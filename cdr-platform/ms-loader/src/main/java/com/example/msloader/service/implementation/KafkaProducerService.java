package com.example.msloader.service.implementation;

import com.example.msloader.model.CDR;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String topic = "cdr";
    private final KafkaTemplate<String, CDR> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, CDR> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce(CDR cdr) {
        kafkaTemplate.send(topic, cdr);
        System.out.println("CDR sent: " + cdr);
    }
}
