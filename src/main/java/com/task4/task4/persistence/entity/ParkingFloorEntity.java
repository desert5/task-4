package com.task4.task4.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ParkingFloorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARKING_FLOOR_SEQ")
    private Long id;

    private Double ceilingHeight;
    private Double maxTotalWeight;
    private Double weightCapacityLeft;
    private Double parkingCapacityLeft;
}
