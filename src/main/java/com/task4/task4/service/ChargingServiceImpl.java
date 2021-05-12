package com.task4.task4.service;

import com.task4.task4.domain.CarData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Emulated charging service
 */
@Slf4j
@Service
public class ChargingServiceImpl implements ChargingService {

    @Override
    public void charge(BigDecimal amount, CarData data) {
        log.info("Car {} was charged with {} credits", data, amount);
    }
}
