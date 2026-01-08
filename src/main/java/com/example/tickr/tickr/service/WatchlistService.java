package com.example.tickr.tickr.service;

import com.example.tickr.tickr.model.User;
import com.example.tickr.tickr.model.Watchlist;
import com.example.tickr.tickr.repository.WatchlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final AuthService authService;

    public WatchlistService(WatchlistRepository watchlistRepository, AuthService authService) {
        this.watchlistRepository = watchlistRepository;
        this.authService = authService;
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
        // Implementation for deleting a watchlist by its ID
        watchlistRepository.deleteById(watchlistId);
    }

}
