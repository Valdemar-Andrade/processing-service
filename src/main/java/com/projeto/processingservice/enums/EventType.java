package com.projeto.processingservice.enums;

public enum EventType {
    RAW_MATERIAL_EXTRACTED,
    TRANSFORMED_MATERIAL;

    public static EventType fromString(String type) {
        return switch (type) {
            case "RAW_MATERIAL_EXTRACTED" -> RAW_MATERIAL_EXTRACTED;
            case "TRANSFORMED_MATERIAL" -> TRANSFORMED_MATERIAL;
            default -> throw new IllegalArgumentException("Unknown event type: " + type);
        };
    }
}
