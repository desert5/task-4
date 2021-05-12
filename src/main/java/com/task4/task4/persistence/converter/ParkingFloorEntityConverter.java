package com.task4.task4.persistence.converter;

import com.task4.task4.domain.ParkingFloor;
import com.task4.task4.persistence.entity.ParkingFloorEntity;
import com.task4.task4.persistence.entity.ParkingSpotEntity;
import com.task4.task4.persistence.repository.ParkingSpotRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class ParkingFloorEntityConverter {

    public static ParkingFloor convert(ParkingFloorEntity entity, ParkingSpotRepository spotRepository) {
        ParkingFloor domain = new ParkingFloor();
        domain.setId(entity.getId());
        domain.setCeilingHeight(entity.getCeilingHeight());
        domain.setMaxTotalWeight(entity.getMaxTotalWeight());
        domain.setWeightCapacityLeft(entity.getWeightCapacityLeft());
        domain.setParkingCapacityLeft(entity.getParkingCapacityLeft());
        domain.setParkingSpots(
                spotRepository.findAllByFloor(entity)
                        .stream()
                        .map(ParkingSpotEntityConverter::convert)
                        .collect(Collectors.toList())
        );
        return domain;
    }

    public static ParkingFloorEntity convert(ParkingFloor domain) {
        ParkingFloorEntity entity = new ParkingFloorEntity();
        entity.setId(domain.getId());
        entity.setCeilingHeight(domain.getCeilingHeight());
        entity.setMaxTotalWeight(domain.getMaxTotalWeight());
        entity.setWeightCapacityLeft(domain.getWeightCapacityLeft() == null ? domain.getMaxTotalWeight() : domain.getWeightCapacityLeft());
        entity.setParkingCapacityLeft(domain.getParkingCapacityLeft() == null ? domain.getParkingSpots().size() : domain.getParkingCapacityLeft());
        return entity;
    }
}
