package com.task4.task4.persistence.service;

import com.task4.task4.domain.CarData;
import com.task4.task4.domain.ParkingFloor;
import com.task4.task4.persistence.converter.ParkingFloorEntityConverter;
import com.task4.task4.persistence.entity.ParkingFloorEntity;
import com.task4.task4.persistence.repository.ParkingFloorRepository;
import com.task4.task4.persistence.repository.ParkingSpotRepository;
import com.task4.task4.service.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingSpotPersistenceService implements ParkingSpotService {

    private final ParkingFloorRepository parkingFloorRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    @Override
    public Optional<ParkingFloor> findFittingFloor(CarData data) {
        Optional<ParkingFloorEntity> floor = parkingFloorRepository.findFittingFloor(data.getHeight(), data.getWeight());
        Optional<ParkingFloor> domainFloor = floor.map(it -> ParkingFloorEntityConverter.convert(it, parkingSpotRepository));
        return domainFloor;
    }

    @Override
    public ParkingFloor getFloorBySpotId(Long id) {
        ParkingFloorEntity floorEntity = parkingSpotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can not find such spot id"))
                .getFloor();
        return ParkingFloorEntityConverter.convert(floorEntity, parkingSpotRepository);
    }

    @Transactional
    @Override
    public void save(ParkingFloor floor) {
        ParkingFloorEntity savedFloor = parkingFloorRepository.save(ParkingFloorEntityConverter.convert(floor));
        parkingSpotRepository.saveAll(ParkingFloorEntityConverter.convertSpots(savedFloor));
    }
}
