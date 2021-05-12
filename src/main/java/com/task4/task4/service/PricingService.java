package com.task4.task4.service;

import com.task4.task4.domain.CarData;
import com.task4.task4.domain.ParkingFloor;
import com.task4.task4.domain.ParkingSpot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class PricingService {

    public BigDecimal pricePerMinute(CarData data, ParkingFloor floor, ParkingSpot spot) {
        return BigDecimal.TEN;
    }

    public BigDecimal getTotalPrice(CarData data, ParkingFloor floor, ParkingSpot spot, Duration timeOfUse) {
        return BigDecimal.TEN;
    }
}
