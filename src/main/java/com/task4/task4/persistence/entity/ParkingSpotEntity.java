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

    private Long occupiedSince;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "CAR_DATA_ID")
    private CarDataEntity occupiedBy;

    @ManyToOne
    @JoinColumn(name = "FLOOR_ID", nullable = false)
    private ParkingFloorEntity floor;
}
