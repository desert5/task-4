package com.task4.task4.persistence.converter;

import com.task4.task4.domain.CarData;
import com.task4.task4.domain.ParkingSpot;
import com.task4.task4.persistence.entity.CarDataEntity;
import com.task4.task4.persistence.entity.ParkingFloorEntity;
import com.task4.task4.persistence.entity.ParkingSpotEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class ParkingSpotEntityConverter {

    public static ParkingSpot convert(ParkingSpotEntity entity) {
        ParkingSpot domain = new ParkingSpot();
        domain.setId(entity.getId());
        domain.setOccupied(entity.isOccupied());
        domain.setOccupiedBy(convert(entity.getOccupiedBy()));
        domain.setOccupiedSince(convert(entity.getOccupiedSince()));
        return domain;
    }

    public static ParkingSpotEntity convert(ParkingSpot domain, ParkingFloorEntity floor) {
        ParkingSpotEntity entity = new ParkingSpotEntity();
        entity.setId(domain.getId());
        entity.setOccupied(domain.isOccupied());
        entity.setOccupiedBy(convert(domain.getOccupiedBy()));
        entity.setOccupiedSince(convert(domain.getOccupiedSince()));
        entity.setFloor(floor);
        return entity;
    }

    public static CarDataEntity convert(CarData domain) {
        if (domain == null) {
            return null;
        }
        CarDataEntity carDataEntity = new CarDataEntity();
        carDataEntity.setId(domain.getId());
        carDataEntity.setHeight(domain.getHeight());
        carDataEntity.setWeight(domain.getWeight());
        return carDataEntity;
    }

    public static CarData convert(CarDataEntity entity) {
        if (entity == null) {
            return null;
        }
        CarData domain = new CarData();
        domain.setId(entity.getId());
        domain.setHeight(entity.getHeight());
        domain.setWeight(entity.getWeight());
        return domain;
    }

    private static Long convert(LocalDateTime date) {
        return date == null ? null : date.toEpochSecond(OffsetDateTime.now().getOffset());
    }

    private static LocalDateTime convert(Long date) {
        return date == null ? null : LocalDateTime.ofEpochSecond(date, 0, OffsetDateTime.now().getOffset());
    }
}
