package com.task4.task4.rest;

import com.task4.task4.domain.ParkingQuote;
import com.task4.task4.rest.response.ParkingQuoteResponse;
import com.task4.task4.service.QuotationService;
import com.task4.task4.domain.CarData;
import com.task4.task4.util.ParkingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
public class RestApi {

    private final QuotationService quotationService;

    @GetMapping("/quote")
    private ResponseEntity<ParkingQuoteResponse> quoteSpot(@RequestParam("weight") Double weight, @RequestParam("height") Double height) {
        Optional<ParkingQuote> quote = quotationService.quoteParkingSpot(new CarData(weight, height));
        if (quote.isPresent()) {
            return ResponseEntity.ok().body(new ParkingQuoteResponse(quote.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/claim")
    private void claimSpot(@PathVariable("id") Long id, @RequestBody CarData data) {
        quotationService.claim(id, data);
    }

    @DeleteMapping("/{id}/claim")
    private void unclaimSpot(@PathVariable("id") Long id) {
        quotationService.unclaim(id);
    }

    @ExceptionHandler(value = {ParkingException.class})
    protected ResponseEntity<?> handleConflict(RuntimeException ex, WebRequest request) {
        log.error("Caught exception in REST API", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
