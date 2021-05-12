package com.task4.task4;

import com.task4.task4.domain.CarData;
import com.task4.task4.domain.ParkingFloor;
import com.task4.task4.domain.ParkingQuote;
import com.task4.task4.domain.ParkingSpot;
import com.task4.task4.persistence.repository.ParkingFloorRepository;
import com.task4.task4.service.ParkingSpotService;
import com.task4.task4.service.QuotationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BasicFlowTests {

	@Autowired
	private QuotationService quotationService;

	@Autowired
	private ParkingSpotService parkingSpotService;

	@Autowired
	private ParkingFloorRepository parkingFloorRepository;

	@BeforeEach
	void defaultSetup() {
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

	@Test
	void shouldAssignSpot() {
		Optional<ParkingQuote> parkingQuote = quotationService.quoteParkingSpot(new CarData(1.0, 2.7));

		assertThat(parkingQuote).isNotEmpty();
		assertThat(parkingQuote.get())
				.extracting(ParkingQuote::getParkingFloor)
				.extracting(ParkingFloor::getId).isEqualTo(2L);
		assertThat(parkingQuote.get()).extracting(ParkingQuote::getPricePerMinute).isEqualTo(BigDecimal.TEN);
	}

	@Test
	void shouldClaimSpot() {
		CarData carData = new CarData(1.0, 2.7);
		Optional<ParkingQuote> parkingQuote = quotationService.quoteParkingSpot(carData);

		Long parkingSpotId = parkingQuote.get().getParkingSpot().getId();
		quotationService.claim(parkingSpotId, carData);

		assertThat(parkingSpotService.getFloorBySpotId(parkingSpotId))
				.satisfies(floor -> {
							assertThat(floor.getWeightCapacityLeft()).isLessThan(floor.getMaxTotalWeight());
							assertThat(floor.getParkingCapacityLeft()).isLessThan(floor.getParkingSpots().size());
							assertThat(floor.getParkingSpots())
									.filteredOn(x -> x.getId().equals(parkingSpotId))
									.extracting(ParkingSpot::getOccupiedBy)
									.first()
									.satisfies(x -> assertThat(x.getWeight()).isEqualTo(1.0));
						});
	}

	@Test
	void shouldRejectIfOversized() {
		Optional<ParkingQuote> parkingQuote = quotationService.quoteParkingSpot(new CarData(1.0, 3.5));

		assertThat(parkingQuote).isEmpty();
	}

	@Test
	void shouldRejectIfOverweight() {
		CarData carData = new CarData(1.0, 2.7);
		Optional<ParkingQuote> parkingQuote = quotationService.quoteParkingSpot(carData);
		quotationService.claim(parkingQuote.get().getParkingSpot().getId(), carData);

		Optional<ParkingQuote> rejectedQuote = quotationService.quoteParkingSpot(new CarData(10.0, 2.7));

		assertThat(rejectedQuote).isEmpty();
	}

}
