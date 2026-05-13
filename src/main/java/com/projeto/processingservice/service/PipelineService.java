package com.projeto.processingservice.service;

import com.projeto.processingservice.dto.BaseEvent;
import com.projeto.processingservice.dto.ProcessedMaterialPayload;
import com.projeto.processingservice.dto.ProducerDTO;
import com.projeto.processingservice.dto.PurposeDTO;
import com.projeto.processingservice.entity.ProcessedMaterial;
import com.projeto.processingservice.model.PipelineStep;
import com.projeto.processingservice.model.ProductionPipeline;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PipelineService {

    private final EventPublisher eventPublisher;

    public PipelineService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Async
    public void execute(ProductionPipeline pipeline, ProcessedMaterial entity, Map<String, Object> rawMaterialPayload) {
        for (PipelineStep step : pipeline.getSteps()) {
            try { Thread.sleep(step.getDurationMs()); } catch (InterruptedException e) { return; }
        }

        ProcessedMaterialPayload payload = new ProcessedMaterialPayload(
                entity.getId().toString(),
                entity.getName(),
                "TRANSFORMED_MATERIAL",
                new ProducerDTO("processing-service", "Steel-Refinery-01"),
                new PurposeDTO("CAR", "VARIOUS", "Material base processado"),
                List.of(rawMaterialPayload)
        );

        BaseEvent event = BaseEvent.create(
                "MATERIAL_PROCESSED",
                "processing-service",
                "component-service",
                payload
        );

        eventPublisher.sendProcessedMaterialEvent(event);
    }
}
