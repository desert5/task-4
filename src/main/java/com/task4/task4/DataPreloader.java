package com.task4.task4;

import com.task4.task4.domain.ParkingFloor;
import com.task4.task4.domain.ParkingSpot;
import com.task4.task4.persistence.repository.ParkingFloorRepository;
import com.task4.task4.service.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataPreloader implements ApplicationRunner {

    private final ParkingSpotService parkingSpotService;
    private final ParkingFloorRepository parkingFloorRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (parkingFloorRepository.findAll().size() == 0) {
            ParkingFloor floor;

            floor = new ParkingFloor();
            floor.setId(1L);
            floor.setMaxTotalWeight(20.0);
            floor.setCeilingHeight(2.5);
            floor.setParkingSpots(Arrays.asList(
                    new ParkingSpot(1L), new ParkingSpot(2L)
            ));

            parkingSpotService.save(floor);

            floor = new ParkingFloor();
            floor.setId(2L);
            floor.setMaxTotalWeight(10.0);
            floor.setCeilingHeight(3.0);
            floor.setParkingSpots(Arrays.asList(
                    new ParkingSpot(3L), new ParkingSpot(4L)
            ));

            parkingSpotService.save(floor);
        }
    }
}
