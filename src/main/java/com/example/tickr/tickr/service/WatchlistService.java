package com.example.tickr.tickr.service;

import com.example.tickr.tickr.common.request.WatchlistItemRequest;
import com.example.tickr.tickr.model.User;
import com.example.tickr.tickr.model.Watchlist;
import com.example.tickr.tickr.model.WatchlistItem;
import com.example.tickr.tickr.model.enums.ReferenceType;
import com.example.tickr.tickr.repository.InstrumentRepository;
import com.example.tickr.tickr.repository.WatchlistItemRepository;
import com.example.tickr.tickr.repository.WatchlistRepository;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final WatchlistItemRepository watchlistItemRepository;
    private final InstrumentRepository instrumentRepository;
    private final AuthService authService;
    private final MarketDataService marketDataService;
    private final WatchlistItemService watchlistItemService;

    public WatchlistService(WatchlistRepository watchlistRepository, WatchlistItemRepository watchlistItemRepository,
                            InstrumentRepository instrumentRepository, AuthService authService, MarketDataService marketDataService, WatchlistItemService watchlistItemService) {
        this.watchlistRepository = watchlistRepository;
        this.watchlistItemRepository = watchlistItemRepository;
        this.instrumentRepository = instrumentRepository;
        this.authService = authService;
        this.marketDataService = marketDataService;
        this.watchlistItemService = watchlistItemService;
    }

    public List<Watchlist> getAllWatchlistsForUser(UUID userId) {
        User user = authService.getUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }

        return watchlistRepository.findByUserId(user.getId());
    }

    public void createWatchlistForUser(String watchlistName, String userEmail) {
        // Implementation for creating a watchlist for a user
        User user = authService.getUserByEmail(userEmail);

        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + userEmail);
        }

        watchlistRepository.save(new Watchlist(watchlistName, user.getId()));
    }

    public void createWatchlistForUser(String watchlistName, UUID userId) {
        User user = authService.getUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }

        watchlistRepository.save(new Watchlist(watchlistName, user.getId()));
    }

    public void renameWatchlist(UUID watchlistId, String newName) {
        // Implementation for renaming a watchlist
        Watchlist watchlist = watchlistRepository.findById(watchlistId)
            .orElseThrow(() -> new IllegalArgumentException("Watchlist not found with ID: " + watchlistId));
        watchlist.setName(newName);
        watchlistRepository.save(watchlist);
    }

    public void deleteWatchlist(UUID watchlistId) {
        watchlistItemService.deleteWatchlistItemsByWatchlistId(watchlistId);
        watchlistRepository.deleteById(watchlistId);
    }

    public void addInstrumentToWatchlist(UUID watchlistId, WatchlistItemRequest watchlistItemRequest) throws IOException, KiteException {
        watchlistRepository.findById(watchlistId).orElseThrow(() -> new IllegalArgumentException("Watchlist not found with ID: " + watchlistId));
        instrumentRepository.findByTradingSymbolAndExchange(
                watchlistItemRequest.getTradingSymbol(),
                watchlistItemRequest.getExchange())
            .orElseThrow(() -> new IllegalArgumentException("Instrument not found with Trading Symbol: " + watchlistItemRequest.getTradingSymbol() + " and Exchange: " + watchlistItemRequest.getExchange()));

        System.out.println("Instrument details: " + watchlistItemRequest.getReferencePrice());
        System.out.println("Reference Type: " + watchlistItemRequest.getReferenceType().name());
        System.out.println("Refernce Type EQ: " + watchlistItemRequest.getReferenceType().equals(ReferenceType.CUSTOM));

        double referencePrice = watchlistItemRequest.getReferenceType().equals(ReferenceType.CUSTOM) ?
            watchlistItemRequest.getReferencePrice() :
            marketDataService.getLTP(
                watchlistItemRequest.getExchange() + ":" +
                    watchlistItemRequest.getTradingSymbol()
            );

        WatchlistItem watchlistItem = WatchlistItem.builder()
            .watchlistId(watchlistId)
            .tradingSymbol(watchlistItemRequest.getTradingSymbol())
            .exchange(watchlistItemRequest.getExchange())
            .referenceType(watchlistItemRequest.getReferenceType())
            .referencePrice(referencePrice)
            .build();

        watchlistItemRepository.save(watchlistItem);
    }

}
