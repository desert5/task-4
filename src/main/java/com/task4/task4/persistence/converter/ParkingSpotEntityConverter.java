package com.task4.task4.persistence.converter;

import com.task4.task4.domain.ParkingSpot;
import com.task4.task4.persistence.entity.ParkingFloorEntity;
import com.task4.task4.persistence.entity.ParkingSpotEntity;

public class ParkingSpotEntityConverter {

    public static ParkingSpot convert(ParkingSpotEntity entity) {
        ParkingSpot domain = new ParkingSpot();
        domain.setId(entity.getId());
        domain.setOccupied(entity.isOccupied());
        return domain;
    }

    public static ParkingSpotEntity convert(ParkingSpot domain, ParkingFloorEntity floor) {
        ParkingSpotEntity entity = new ParkingSpotEntity();
        entity.setId(domain.getId());
        entity.setOccupied(domain.isOccupied());
        entity.setFloor(floor);
        return entity;
    }
}
