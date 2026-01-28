package com.example.tickr.tickr.service;

import com.example.tickr.tickr.common.response.QuoteResponse;
import com.zerodhatech.models.Quote;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MarketService {

    private final MarketDataService marketDataService;

    public MarketService(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    public List<QuoteResponse> getMarketOverview() {
        // Implementation to get market overview using marketDataService
        List<QuoteResponse> quoteResponses = new java.util.ArrayList<>();

        Map<String, Quote> overview = marketDataService.getMarketOverview();
        for (Map.Entry<String, Quote> e : overview.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue().lastPrice);
            quoteResponses.add(QuoteResponse.builder()
                .instrumentName(e.getKey().split(":")[1])
                .exchange(e.getKey().split(":")[0])
                .lastPrice(e.getValue().lastPrice)
                .high(e.getValue().ohlc.high)
                .low(e.getValue().ohlc.low)
                .open(e.getValue().ohlc.open)
                .close(e.getValue().ohlc.close)
                .build());
        }
        return quoteResponses;
    }
}
