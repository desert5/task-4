package com.task4.task4.service;

import com.task4.task4.domain.CarData;
import com.task4.task4.domain.ParkingFloor;

import java.util.List;
import java.util.Optional;

public interface ParkingSpotService {

    Optional<ParkingFloor> findFloorForCar(CarData data);

    ParkingFloor getFloorBySpotId(Long id);

    void save(ParkingFloor floor);
}
