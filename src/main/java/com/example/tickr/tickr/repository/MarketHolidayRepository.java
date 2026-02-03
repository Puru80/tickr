package com.example.tickr.tickr.repository;

import com.example.tickr.tickr.model.MarketHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface MarketHolidayRepository extends JpaRepository<MarketHoliday, Long> {
    Optional<MarketHoliday> findByHolidayDate(String holidayDate);
    boolean existsByHolidayDate(String holidayDate);
}
