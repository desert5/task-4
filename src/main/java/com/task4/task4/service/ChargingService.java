package com.task4.task4.service;

import com.task4.task4.domain.CarData;

import java.math.BigDecimal;

public interface ChargingService {
    void charge(BigDecimal amount, CarData car);
}
