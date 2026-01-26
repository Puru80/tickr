package com.example.tickr.tickr.repository;

import com.example.tickr.tickr.model.InstrumentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstrumentRepository extends JpaRepository<InstrumentInfo, UUID> {
    Page<InstrumentInfo> findByTradingSymbolContainingIgnoreCaseOrNameContainingIgnoreCase(String tradingSymbol, String name, Pageable pageable);

    Optional<InstrumentInfo> findByTradingSymbolAndExchange(String tradingSymbol, String exchange);
}
