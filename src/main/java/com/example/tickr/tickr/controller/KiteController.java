package com.example.tickr.tickr.controller;

import com.example.tickr.tickr.service.KiteConnectService;
import com.example.tickr.tickr.service.MarketDataService;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/kite")
public class KiteController {

    private final KiteConnectService kiteConnectService;
    private final MarketDataService marketDataService;

    public KiteController(KiteConnectService kiteConnectService, MarketDataService marketDataService) {
        this.kiteConnectService = kiteConnectService;
        this.marketDataService = marketDataService;
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
//        marketDataService.getHistoricalData();
        marketDataService.getQuote();
        return "KiteConnect Data Fetch";
    }

}
