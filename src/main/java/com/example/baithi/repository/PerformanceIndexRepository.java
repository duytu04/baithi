package com.example.baithi.repository;

import com.example.baithi.model.PerformanceIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerformanceIndexRepository extends JpaRepository<PerformanceIndex, Long> {
    Optional<PerformanceIndex> findByNameIgnoreCase(String name);
}
