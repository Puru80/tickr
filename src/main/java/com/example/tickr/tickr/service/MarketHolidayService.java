package com.example.tickr.tickr.service;

import com.example.tickr.tickr.model.MarketHoliday;
import com.example.tickr.tickr.repository.MarketHolidayRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MarketHolidayService {

    private final MarketHolidayRepository marketHolidayRepository;

    public MarketHolidayService(MarketHolidayRepository marketHolidayRepository) {
        this.marketHolidayRepository = marketHolidayRepository;
    }

    public boolean isHoliday(LocalDate date) {
        // Assuming holidayDate in MarketHoliday is stored as "YYYY-MM-DD" string
        String formattedDate = date.format(DateTimeFormatter.ISO_DATE);
        return marketHolidayRepository.existsByHolidayDate(formattedDate);
    }

    public List<MarketHoliday> getAllHolidays() {
        return marketHolidayRepository.findAll();
    }
}
