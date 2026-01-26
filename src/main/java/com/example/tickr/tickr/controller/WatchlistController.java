package com.example.tickr.tickr.controller;

import com.example.tickr.tickr.TickrResponse;
import com.example.tickr.tickr.common.request.CreateWatchlistRequest;
import com.example.tickr.tickr.common.request.WatchlistItemRequest;
import com.example.tickr.tickr.common.utils.RequestUtils;
import com.example.tickr.tickr.service.WatchlistItemService;
import com.example.tickr.tickr.service.WatchlistService;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/watchlists")
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final WatchlistItemService watchlistItemService;

    public WatchlistController(WatchlistService watchlistService, WatchlistItemService watchlistItemService) {
        this.watchlistService = watchlistService;
        this.watchlistItemService = watchlistItemService;
    }

    @GetMapping
    public ResponseEntity<TickrResponse> getWatchlists() {
        return new ResponseEntity<>(
            new TickrResponse("Successfully retrieved watchlists",
                watchlistService.getAllWatchlistsForUser(RequestUtils.getUserIdFromRequest())),
            org.springframework.http.HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<TickrResponse> createWatchlist(@RequestBody CreateWatchlistRequest watchlistRequest) {
        watchlistService.createWatchlistForUser(watchlistRequest.getName(), RequestUtils.getUserIdFromRequest());
        return new ResponseEntity<>(
            new TickrResponse("Successfully created watchlist", null),
            org.springframework.http.HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TickrResponse> renameWatchlist(@PathVariable String id,
                                                         @RequestBody CreateWatchlistRequest watchlistRequest) {
        watchlistService.renameWatchlist(UUID.fromString(id), watchlistRequest.getName());
        return new ResponseEntity<>(
            new TickrResponse("Successfully renamed watchlist", null),
            org.springframework.http.HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TickrResponse> deleteWatchlist(@PathVariable String id) {
        watchlistService.deleteWatchlist(UUID.fromString(id));
        return new ResponseEntity<>(
            new TickrResponse("Successfully deleted watchlist", null),
            org.springframework.http.HttpStatus.OK
        );
    }

    @GetMapping("/{id}/instruments")
    public ResponseEntity<TickrResponse> getWatchlistItems(@PathVariable String id) throws IOException, KiteException {
        return new ResponseEntity<>(
            new TickrResponse("Instruments in watchlist: " + id,
                watchlistItemService.getWatchlistItemsByWatchlistId(UUID.fromString(id))),
            HttpStatus.OK);

    }

    @PostMapping("/{id}/instruments")
    public ResponseEntity<TickrResponse> addInstrumentToWatchlist(@PathVariable(name = "id") String watchlistId,
                                                                  @RequestBody WatchlistItemRequest watchlistItemRequest) throws IOException, KiteException {
        watchlistService.addInstrumentToWatchlist(UUID.fromString(watchlistId), watchlistItemRequest);
        return new ResponseEntity<>(
            new TickrResponse("Successfully added instrument to watchlist",
                null),
            org.springframework.http.HttpStatus.OK
        );
    }

    @DeleteMapping("/instruments/{id}")
    public ResponseEntity<TickrResponse> deleteWatchlistItem(@PathVariable String id) {
        watchlistItemService.deleteWatchlistItemById(UUID.fromString(id));
        return new ResponseEntity<>(
            new TickrResponse("Successfully deleted watchlist item", null),
            org.springframework.http.HttpStatus.OK
        );
    }
}
