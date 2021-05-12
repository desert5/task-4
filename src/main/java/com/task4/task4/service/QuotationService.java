package com.task4.task4.service;

import com.task4.task4.domain.CarData;
import com.task4.task4.domain.ParkingFloor;
import com.task4.task4.domain.ParkingQuote;
import com.task4.task4.domain.ParkingSpot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuotationService {

    private final ParkingSpotService parkingSpotService;
    private final PricingService pricingService;

    public Optional<ParkingQuote> quoteParkingSpot(CarData data) {
        Optional<ParkingFloor> floor = parkingSpotService.findFittingFloor(data);
        if (floor.isPresent()) {
            Optional<ParkingSpot> spot = floor.get().getParkingSpots().stream().filter(x -> !x.isOccupied()).findFirst();
            if (spot.isPresent()) {
                return Optional.of(new ParkingQuote(pricingService.pricePerMinute(data, floor.get(), spot.get()),
                        spot.get(), floor.get()));
            }
        }
        return Optional.empty();
    }

    /**
     * Since in most cases cars will not enter parking in parallel I will this code non thread-safe for simplicity,
     * unless specified otherwise.
     */
    @Transactional
    public void claim(Long id, CarData data) {
        ParkingFloor parkingFloor = parkingSpotService.getFloorBySpotId(id);
        ParkingSpot parkingSpot = parkingFloor.getParkingSpots()
                .stream()
                .filter(spot -> spot.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can not claim this spot - it doesn't exist"));

        parkingFloor.setWeightCapacityLeft(parkingFloor.getWeightCapacityLeft() - data.getWeight());
        parkingFloor.setParkingCapacityLeft(parkingFloor.getParkingCapacityLeft() - 1);
        parkingSpot.setOccupied(true);

        parkingSpotService.save(parkingFloor);
    }
}
