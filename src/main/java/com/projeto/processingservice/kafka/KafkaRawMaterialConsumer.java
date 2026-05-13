package com.projeto.processingservice.kafka;

import com.projeto.processingservice.dto.BaseEvent;
import com.projeto.processingservice.enums.EventType;
import com.projeto.processingservice.service.ProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaRawMaterialConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaRawMaterialConsumer.class);
    private final ProcessingService processingService;

    public KafkaRawMaterialConsumer(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @KafkaListener(id = "processing-service", topics = "industria-carro")
    public void consume(BaseEvent event) {
        if (EventType.RAW_MATERIAL_EXTRACTED.name().equals(event.eventType())) {
            logger.info("[processing-service] Matéria-prima recebida para processamento: {}", event.eventId());
            processingService.process(event);
        }
    }
}
