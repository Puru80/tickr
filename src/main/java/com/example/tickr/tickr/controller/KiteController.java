package com.example.tickr.tickr.controller;

import com.example.tickr.tickr.service.InstrumentService;
import com.example.tickr.tickr.service.KiteConnectService;
import com.example.tickr.tickr.service.MarketDataService;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/kite")
public class KiteController {

    private final KiteConnectService kiteConnectService;
    private final MarketDataService marketDataService;
    private final InstrumentService instrumentService;

    public KiteController(KiteConnectService kiteConnectService, MarketDataService marketDataService, InstrumentService instrumentService) {
        this.kiteConnectService = kiteConnectService;
        this.marketDataService = marketDataService;
        this.instrumentService = instrumentService;
    }

    @GetMapping("/session")
    public String getStatus(@RequestParam String action,
                            @RequestParam String status,
                            @RequestParam(name = "request_token") String requestToken ) throws IOException, KiteException {
        System.out.println("Action: " + action);
        System.out.println("Status: " + status);
        System.out.println("Request Token: " + requestToken);
        KiteConnect authenticatedKiteConnect = kiteConnectService.generateSession(requestToken);
        System.out.println("KiteConnect session successfully generated. Access Token: " + authenticatedKiteConnect.getAccessToken());

        return "KiteConnect session successfully generated. Access Token: " + authenticatedKiteConnect.getAccessToken();
    }

    @GetMapping("/market-data")
    public String fetchMarketData() throws IOException, KiteException {
        marketDataService.fetchInstruments();
        return "KiteConnect Data Fetch";
    }

    @PostMapping("/update-instruments")
    public String updateInstruments() {
        instrumentService.updateInstruments();
        return "Instruments updated successfully.";
    }

    @GetMapping("/quote")
    public ResponseEntity<String> get() throws IOException, KiteException {
        marketDataService.getQuote();
        return ResponseEntity.ok("Kite Controller is working!");
    }

}
