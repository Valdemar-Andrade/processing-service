package com.projeto.processingservice.repository;

import com.projeto.processingservice.entity.ProcessedMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProcessedMaterialRepository extends JpaRepository<ProcessedMaterial, UUID> {
}
