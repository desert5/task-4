package com.task4.task4.domain;

import lombok.Data;

@Data
public class CarData {
    private Long id;
    private Double weight;
    private Double height;

    public CarData() {
    }

    public CarData(Double weight, Double height) {
        this.weight = weight;
        this.height = height;
    }
}
