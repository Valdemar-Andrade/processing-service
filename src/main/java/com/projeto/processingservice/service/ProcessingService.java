package com.projeto.processingservice.service;

import com.projeto.processingservice.dto.BaseEvent;
import com.projeto.processingservice.entity.ProcessedMaterial;
import com.projeto.processingservice.model.PipelineStep;
import com.projeto.processingservice.model.ProductionPipeline;
import com.projeto.processingservice.repository.ProcessedMaterialRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProcessingService {

    private final ProcessedMaterialRepository repository;
    private final PipelineService pipelineService;

    public ProcessingService(ProcessedMaterialRepository repository, PipelineService pipelineService) {
        this.repository = repository;
        this.pipelineService = pipelineService;
    }

    @Transactional
    public void process(BaseEvent rawEvent) {
        Map<String, Object> rawData = (Map<String, Object>) rawEvent.payload();

        String transformedName = transform(rawData.get("name").toString());

        ProcessedMaterial processed = ProcessedMaterial.builder()
                .name(transformedName)
                .type("TRANSFORMED_MATERIAL")
                .sourceRawMaterialId(rawData.get("id").toString())
                .quantity(1)
                .build();
        repository.save(processed);

        ProductionPipeline pipeline = buildProcessingPipeline(transformedName);

        pipelineService.execute(pipeline, processed, rawData);
    }

    private String transform(String rawMaterial) {
        return switch (rawMaterial.toUpperCase()) {
            case "IRON" -> "STEEL";
            case "LATEX" -> "RUBBER";
            case "BAUXITE" -> "ALUMINUM";
            case "LITHIUM" -> "REFINED_LITHIUM";
            default -> "PROCESSED_" + rawMaterial;
        };
    }

    private ProductionPipeline buildProcessingPipeline(String name) {
        return ProductionPipeline.builder()
                .name("PROCESSING_PIPELINE_" + name)
                .steps(List.of(
                        new PipelineStep("REFINING", 8000L),
                        new PipelineStep("PURIFICATION", 6000L),
                        new PipelineStep("MATERIAL_STANDARDIZATION", 4000L)
                )).build();
    }
}
