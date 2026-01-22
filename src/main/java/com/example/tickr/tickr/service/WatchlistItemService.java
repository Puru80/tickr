package com.example.tickr.tickr.service;

import com.example.tickr.tickr.model.WatchlistItem;
import com.example.tickr.tickr.model.enums.ReferenceType;
import com.example.tickr.tickr.repository.WatchlistItemRepository;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.OHLCQuote;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class WatchlistItemService {

    private final WatchlistItemRepository watchlistItemRepository;
    private final MarketDataService marketDataService;

    public WatchlistItemService(WatchlistItemRepository watchlistItemRepository, MarketDataService marketDataService) {
        this.watchlistItemRepository = watchlistItemRepository;
        this.marketDataService = marketDataService;
    }

    public List<WatchlistItem> getWatchlistItemsByWatchlistId(UUID watchlistId) throws IOException, KiteException {
        List<WatchlistItem> items = watchlistItemRepository.findWatchlistItemByWatchlistId(watchlistId);
        if(items.isEmpty()) {
            return items;
        }

        String[] tokens = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            tokens[i] = items.get(i).getExchange() + ":" + items.get(i).getTradingSymbol();
        }

        Map<String, OHLCQuote> quoteMap = marketDataService.getOHLC(tokens);

        for(WatchlistItem item : items) {
            if(!item.getReferenceType().equals(ReferenceType.CUSTOM)) {
                item.setReferencePrice(quoteMap.get(item.getExchange() + ":" + item.getTradingSymbol()).ohlc.close);
            }

            item.setLastPrice(quoteMap.get(item.getExchange() + ":" + item.getTradingSymbol()).lastPrice);
        }

        return watchlistItemRepository.findWatchlistItemByWatchlistId(watchlistId);
    }


}
