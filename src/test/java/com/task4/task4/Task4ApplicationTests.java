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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Task4ApplicationTests {

	@Autowired
	private QuotationService quotationService;

	@Autowired
	private ParkingSpotService parkingSpotService;

	@BeforeEach
	void setup() {
		ParkingFloor floor;

		floor = new ParkingFloor();
		floor.setId(1L);
		floor.setMaxTotalWeight(20.0);
		floor.setCeilingHeight(2.5);
		floor.setParkingSpots(Arrays.asList(
				new ParkingSpot(1L, false), new ParkingSpot(2L, false)
		));

		parkingSpotService.save(floor);

		floor = new ParkingFloor();
		floor.setId(2L);
		floor.setMaxTotalWeight(10.0);
		floor.setCeilingHeight(3.0);
		floor.setParkingSpots(Arrays.asList(
				new ParkingSpot(3L, false), new ParkingSpot(4L, false)
		));

		parkingSpotService.save(floor);
	}

	@Test
	void contextLoads() {
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

		quotationService.claim(parkingQuote.get().getParkingSpot().getId(), carData);

		assertThat(parkingSpotService.getFloorBySpotId(parkingQuote.get().getParkingSpot().getId()))
				.satisfies(floor -> {
							assertThat(floor.getWeightCapacityLeft()).isLessThan(floor.getMaxTotalWeight());
							assertThat(floor.getParkingCapacityLeft()).isLessThan(floor.getParkingSpots().size());
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
