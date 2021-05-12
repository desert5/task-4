package com.task4.task4.persistence.repository;

import com.task4.task4.persistence.entity.ParkingFloorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingFloorRepository extends JpaRepository<ParkingFloorEntity, Long> {
    Optional<ParkingFloorEntity> findFirstByCeilingHeightGreaterThanAndWeightCapacityLeftGreaterThanOrderByParkingCapacityLeftDesc(Double carHeight, Double carWeight);

    default Optional<ParkingFloorEntity> findFittingFloor(Double carHeight, Double carWeight) {
        return findFirstByCeilingHeightGreaterThanAndWeightCapacityLeftGreaterThanOrderByParkingCapacityLeftDesc(carHeight, carWeight);
    }
}
