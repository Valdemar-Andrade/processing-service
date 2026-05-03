package com.projeto.processingservice.kafka;

import com.projeto.processingservice.dto.BaseEvent;
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

    @KafkaListener(topics = "industria-carro", groupId = "processing-group")
    public void consume(BaseEvent event) {
        if ("RAW_MATERIAL_EXTRACTED".equals(event.eventType())) {
            logger.info("[KAFKA] Matéria-prima recebida para processamento: {}", event.eventId());
            processingService.process(event);
        }
    }
}
