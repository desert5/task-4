package com.task4.task4.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ParkingQuote {
    private BigDecimal pricePerMinute;
    private ParkingSpot parkingSpot;
    private ParkingFloor parkingFloor;
}
