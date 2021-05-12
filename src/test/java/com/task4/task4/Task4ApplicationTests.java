package com.task4.task4;

import com.task4.task4.domain.CarData;
import com.task4.task4.domain.ParkingFloor;
import com.task4.task4.domain.ParkingQuote;
import com.task4.task4.domain.ParkingSpot;
import com.task4.task4.service.ParkingSpotService;
import com.task4.task4.service.QuotationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

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
		floor.setWeightCapacityLeft(10.0);
		floor.setCeilingHeight(3.0);
		floor.setParkingSpots(Arrays.asList(
				new ParkingSpot(), new ParkingSpot()
		));

		parkingSpotService.save(floor);

		floor = new ParkingFloor();
		floor.setWeightCapacityLeft(20.0);
		floor.setCeilingHeight(2.5);
		floor.setParkingSpots(Arrays.asList(
				new ParkingSpot(), new ParkingSpot()
		));

		parkingSpotService.save(floor);
	}

	@Test
	void contextLoads() {
	}

	@Test
	void test() {
		Optional<ParkingQuote> parkingQuote = quotationService.quoteParkingSpot(new CarData(1.0, 2.7));

		assertThat(parkingQuote).isNotEmpty();
	}

}
