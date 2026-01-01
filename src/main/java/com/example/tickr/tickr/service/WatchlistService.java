package com.example.tickr.tickr.service;

import com.example.tickr.tickr.model.User;
import com.example.tickr.tickr.model.Watchlist;
import com.example.tickr.tickr.repository.WatchlistRepository;
import org.springframework.stereotype.Service;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final AuthService authService;

    public WatchlistService(WatchlistRepository watchlistRepository, AuthService authService) {
        this.watchlistRepository = watchlistRepository;
        this.authService = authService;
    }

    public void createWatchlistForUser(String watchlistName, String userEmail) {
        // Implementation for creating a watchlist for a user
        User user = authService.getUserByEmail(userEmail);

        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + userEmail);
        }

        watchlistRepository.save(new Watchlist(watchlistName, user.getId()));
    }

}
