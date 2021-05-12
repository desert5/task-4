package com.task4.task4.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CarDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAR_DATA_SEQ")
    private Long id;

    private Double weight;
    private Double height;
}
