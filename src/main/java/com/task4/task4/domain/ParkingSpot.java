package com.task4.task4.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class ParkingSpot {
    private Long id;
    private boolean occupied;
}
