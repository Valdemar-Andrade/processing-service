package com.projeto.processingservice.dto;

import java.util.List;

public record ProcessedMaterialPayload(
        String id,
        String name,
        String type, // "TRANSFORMED_MATERIAL"
        ProducerDTO producer,
        PurposeDTO purpose,
        List<Object> components // Aqui irá o RawMaterialPayload original
) { }
