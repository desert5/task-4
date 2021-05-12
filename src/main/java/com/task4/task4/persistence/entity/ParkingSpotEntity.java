package com.task4.task4.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ParkingSpotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARKING_SPOT_SEQ")
    private Long id;

    private boolean occupied;

    @ManyToOne
    @JoinColumn(name = "FLOOR_ID", nullable = false)
    private ParkingFloorEntity floor;
}
