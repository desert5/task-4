package com.task4.task4.service;

import com.task4.task4.domain.CarData;
import com.task4.task4.domain.ParkingFloor;
import com.task4.task4.domain.ParkingQuote;
import com.task4.task4.domain.ParkingSpot;
import com.task4.task4.util.ParkingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.task4.task4.util.Util.inRange;

@Service
@RequiredArgsConstructor
public class QuotationService {

    private final ParkingSpotService parkingSpotService;
    private final PricingService pricingService;
    private final ChargingService chargingService;

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
     * Since in most cases cars will not enter parking in parallel I will leave this code non thread-safe for simplicity,
     * unless it will be specified otherwise.
     */
    @Transactional
    public void claim(Long id, CarData data) {
        ParkingFloor parkingFloor = parkingSpotService.getFloorBySpotId(id);
        ParkingSpot parkingSpot = parkingFloor.getParkingSpots()
                .stream()
                .filter(spot -> spot.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can not claim this spot - it doesn't exist"));

        if (!parkingSpot.isOccupied()) {
            parkingSpot.setOccupied(true);
            parkingSpot.setOccupiedBy(data);
            parkingSpot.setOccupiedSince(LocalDateTime.now());

            parkingFloor.setWeightCapacityLeft(parkingFloor.getWeightCapacityLeft() - data.getWeight());
            parkingFloor.setParkingCapacityLeft(percentageOfVacantSpots(parkingFloor));

            parkingSpotService.save(parkingFloor);
        } else {
            throw new ParkingException("The spot is already claimed");
        }
    }

    @Transactional
    public void unclaim(Long id) {
        ParkingFloor parkingFloor = parkingSpotService.getFloorBySpotId(id);
        ParkingSpot parkingSpot = parkingFloor.getParkingSpots()
                .stream()
                .filter(spot -> spot.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ParkingException("Can not claim this spot - it doesn't exist"));

        if (parkingSpot.isOccupied()) {
            CarData carData = parkingSpot.getOccupiedBy();

            BigDecimal totalPrice = pricingService.getTotalPrice(carData, parkingFloor, parkingSpot,
                    Duration.between(parkingSpot.getOccupiedSince(), LocalDateTime.now()));

            chargingService.charge(totalPrice, carData);

            parkingSpot.setOccupied(false);
            parkingSpot.setOccupiedBy(null);
            parkingSpot.setOccupiedSince(null);

            parkingFloor.setParkingCapacityLeft(percentageOfVacantSpots(parkingFloor));
            parkingFloor.setWeightCapacityLeft(
                    inRange(parkingFloor.getWeightCapacityLeft() + carData.getWeight(),
                            0.0, parkingFloor.getMaxTotalWeight())
            );

            parkingSpotService.save(parkingFloor);
        }
    }

    private double percentageOfVacantSpots(ParkingFloor parkingFloor) {
        return (double) parkingFloor.getParkingSpots().stream().filter(it -> !it.isOccupied()).count()
                / parkingFloor.getParkingSpots().size();
    }
}
