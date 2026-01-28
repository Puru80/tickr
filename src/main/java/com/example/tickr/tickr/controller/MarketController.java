package com.example.tickr.tickr.controller;

import com.example.tickr.tickr.TickrResponse;
import com.example.tickr.tickr.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/market")
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;

    @GetMapping("/overview")
    public ResponseEntity<TickrResponse> getMarketOverview() {

        return new ResponseEntity<>(
            new TickrResponse("Market overview fetched successfully",
                marketService.getMarketOverview()),  // Replace null with actual data
            HttpStatus.OK
        );
    }
}
