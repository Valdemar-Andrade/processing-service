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
        // 1. Extrai o payload da matéria-prima
        // Nota: O payload chega como LinkedHashMap, você pode converter para o seu DTO
        Map<String, Object> rawData = (Map<String, Object>) rawEvent.payload();

        // 2. Lógica de Transformação
        String transformedName = transform(rawData.get("name").toString());

        // 3. Persiste o início do refinamento
        ProcessedMaterial processed = ProcessedMaterial.builder()
                .name(transformedName)
                .type("TRANSFORMED_MATERIAL")
                .sourceRawMaterialId(rawData.get("id").toString())
                .quantity(1)
                .build();
        repository.save(processed);

        // 4. Cria o Pipeline de Refino conforme o PDF[cite: 1]
        ProductionPipeline pipeline = buildProcessingPipeline(transformedName);

        // 5. Executa a simulação temporal e depois envia MATERIAL_PROCESSED[cite: 1]
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
