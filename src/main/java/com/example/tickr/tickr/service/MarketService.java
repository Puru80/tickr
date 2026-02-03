package com.example.tickr.tickr.service;

import com.example.tickr.tickr.common.response.MarketStatusResponse;
import com.example.tickr.tickr.common.response.QuoteResponse;
import com.zerodhatech.models.Quote;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Map;

@Service
public class MarketService {

    private final MarketDataService marketDataService;
    private final MarketHolidayService marketHolidayService;

    private static final ZoneId istZone = ZoneId.of("Asia/Kolkata");


    public MarketService(MarketDataService marketDataService, MarketHolidayService marketHolidayService) {
        this.marketDataService = marketDataService;
        this.marketHolidayService = marketHolidayService;
    }

    public List<QuoteResponse> getMarketOverview() {
        // Implementation to get market overview using marketDataService
        List<QuoteResponse> quoteResponses = new java.util.ArrayList<>();

        Map<String, Quote> overview = marketDataService.getMarketOverview();
        for (Map.Entry<String, Quote> e : overview.entrySet()) {
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

    public MarketStatusResponse getMarketStatus() {
        LocalDate today = LocalDate.now(istZone);
        LocalTime currentTime = LocalTime.now(istZone);

        DayOfWeek dayOfWeek = today.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || marketHolidayService.isHoliday(today)) {
            return MarketStatusResponse.builder()
                .isOpen(false)
                .build();
        }

        // Define market hours (09:15 AM to 03:30 PM IST)
        LocalTime marketOpenTime = LocalTime.of(9, 15);
        LocalTime marketCloseTime = LocalTime.of(15, 30);

        marketOpenTime = ZonedDateTime.of(today, marketOpenTime, istZone).toLocalTime();
        marketCloseTime = ZonedDateTime.of(today, marketCloseTime, istZone).toLocalTime();

        // Check if current time is within market hours
        if (currentTime.isAfter(marketOpenTime) && currentTime.isBefore(marketCloseTime)) {
            return MarketStatusResponse.builder()
                .isOpen(true)
                .build();
        }

        return MarketStatusResponse.builder()
            .isOpen(false)
            .build();

    }
}
