package com.example.tickr.tickr.controller;

import com.example.tickr.tickr.TickrResponse;
import com.example.tickr.tickr.common.utils.RequestUtils;
import com.example.tickr.tickr.service.WatchlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/watchlists")
public class WatchlistController {

    private final WatchlistService watchlistService;

    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @GetMapping
    public ResponseEntity<TickrResponse> testEndpoint() {
        return new ResponseEntity<>(
            new TickrResponse("Successfully retrieved watchlists",
                watchlistService.getAllWatchlistsForUser(RequestUtils.getUserIdFromRequest())),
            org.springframework.http.HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<TickrResponse> createWatchlist(@RequestBody String watchlistName) {
        watchlistService.createWatchlistForUser(watchlistName, RequestUtils.getUserIdFromRequest());
        return new ResponseEntity<>(
            new TickrResponse("Successfully created watchlist", null),
            org.springframework.http.HttpStatus.OK
        );
    }

    @GetMapping("/{id}/instruments")
    public ResponseEntity<String> get(@PathVariable String id) {
        return new ResponseEntity<>("Instruments in watchlist " + id, HttpStatus.OK);

    }
}
