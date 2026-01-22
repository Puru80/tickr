package com.example.tickr.tickr.service;

import com.example.tickr.tickr.model.InstrumentInfo;
import com.example.tickr.tickr.repository.InstrumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstrumentService {

    private final MarketDataService marketDataService;
    private final InstrumentRepository instrumentRepository;

    public InstrumentService(MarketDataService marketDataService, InstrumentRepository instrumentRepository) {
        this.marketDataService = marketDataService;
        this.instrumentRepository = instrumentRepository;
    }

    public Page<InstrumentInfo> searchInstruments(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return instrumentRepository.findByTradingSymbolContainingIgnoreCaseOrNameContainingIgnoreCase(query, query, pageable);
    }

    public void updateInstruments() {
        instrumentRepository.deleteAllInBatch();
        List<InstrumentInfo> instruments = marketDataService.getInstrumentDetailsFromCSV();

        instrumentRepository.saveAll(instruments);
    }


}
