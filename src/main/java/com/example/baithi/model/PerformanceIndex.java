package com.example.baithi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "performance_indexes")
public class PerformanceIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "value_min", nullable = false)
    private double valueMin;

    @Column(name = "value_max", nullable = false)
    private double valueMax;

    protected PerformanceIndex() {
    }

    public PerformanceIndex(String name, double valueMin, double valueMax) {
        this.name = name;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getValueMin() {
        return valueMin;
    }

    public double getValueMax() {
        return valueMax;
    }
}
