package com.task4.task4.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "id")
public class ParkingSpot {
    private Long id;
    private boolean occupied;
    private LocalDateTime occupiedSince;
    private CarData occupiedBy;

    public ParkingSpot() {
    }

    public ParkingSpot(Long id) {
        this.id = id;
    }
}
