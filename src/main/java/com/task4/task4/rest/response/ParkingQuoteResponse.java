package com.task4.task4.rest.response;

import com.task4.task4.domain.ParkingQuote;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParkingQuoteResponse {
    private Long parkingSpotId;
    private BigDecimal pricePerMinute;

    public ParkingQuoteResponse(ParkingQuote quote) {
        parkingSpotId = quote.getParkingSpot().getId();
        pricePerMinute = quote.getPricePerMinute();
    }
}
