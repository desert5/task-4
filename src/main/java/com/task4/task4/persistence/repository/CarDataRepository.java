package com.task4.task4.persistence.repository;

import com.task4.task4.persistence.entity.CarDataEntity;
import com.task4.task4.persistence.entity.ParkingFloorEntity;
import com.task4.task4.persistence.entity.ParkingSpotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CarDataRepository extends JpaRepository<CarDataEntity, Long> {
}
