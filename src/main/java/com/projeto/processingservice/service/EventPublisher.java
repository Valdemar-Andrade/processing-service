package com.projeto.processingservice.service;

import com.projeto.processingservice.dto.BaseEvent;

public interface EventPublisher {
    void sendProcessedMaterialEvent(BaseEvent materialEvent);
}
