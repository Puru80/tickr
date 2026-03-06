package com.example.tickr.tickr.service;

import com.example.tickr.tickr.model.WatchlistItem;
import com.example.tickr.tickr.model.enums.ReferenceType;
import com.example.tickr.tickr.repository.WatchlistItemRepository;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.OHLCQuote;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class WatchlistItemService {

    private final Logger logger = LoggerFactory.getLogger(WatchlistItemService.class);
    private final WatchlistItemRepository watchlistItemRepository;
    private final MarketDataService marketDataService;

    public WatchlistItemService(WatchlistItemRepository watchlistItemRepository, MarketDataService marketDataService) {
        this.watchlistItemRepository = watchlistItemRepository;
        this.marketDataService = marketDataService;
    }

    public List<WatchlistItem> getWatchlistItemsByWatchlistId(UUID watchlistId) throws IOException, KiteException {
        logger.info("Fetching watchlist items for watchlistId: {}", watchlistId);

        List<WatchlistItem> items = watchlistItemRepository.findWatchlistItemByWatchlistId(watchlistId);
        logger.info("Found {} items in watchlist", items.size());

        if(items.isEmpty()) {
            return items;
        }

        String[] tokens = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            tokens[i] = items.get(i).getExchange() + ":" + items.get(i).getTradingSymbol();
        }

        Map<String, OHLCQuote> quoteMap = marketDataService.getOHLC(tokens);

        for(WatchlistItem item : items) {
            String token = item.getExchange() + ":" + item.getTradingSymbol();

            if(!quoteMap.containsKey(token)) {
                logger.warn("No market data found for token: {}", token);
                item.setLastPrice(0.0);
                continue;
            }

            if(!item.getReferenceType().equals(ReferenceType.CUSTOM)) {
                item.setReferencePrice(quoteMap.get(token).ohlc.close);
            }

            item.setLastPrice(quoteMap.get(token).lastPrice);
        }

        return items;
    }

    @Transactional
    public void deleteWatchlistItemsByWatchlistId(UUID watchlistId) {
        watchlistItemRepository.deleteAllByWatchlistId(watchlistId);
    }

    public void deleteWatchlistItemById(UUID instrumentId) {
        watchlistItemRepository.deleteById(instrumentId);
    }
}
