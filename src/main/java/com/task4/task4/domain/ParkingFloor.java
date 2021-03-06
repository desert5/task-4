package com.task4.task4.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

@Data
@EqualsAndHashCode(of = "id")
public class ParkingFloor {
    private Long id;
    private Double ceilingHeight;
    private Double maxTotalWeight;
    private Double weightCapacityLeft;
    private Double parkingCapacityLeft;
    private Collection<ParkingSpot> parkingSpots;
}
