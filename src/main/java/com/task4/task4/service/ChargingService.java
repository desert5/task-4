package com.task4.task4.service;

import com.task4.task4.domain.CarData;
import com.task4.task4.domain.ParkingFloor;

import javax.smartcardio.Card;
import java.math.BigDecimal;
import java.util.Optional;

public interface ChargingService {
    void charge(BigDecimal amount, CarData car);
}
