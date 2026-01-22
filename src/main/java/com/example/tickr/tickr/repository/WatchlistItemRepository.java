package com.example.tickr.tickr.repository;

import com.example.tickr.tickr.model.WatchlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WatchlistItemRepository extends JpaRepository<WatchlistItem, UUID> {
    List<WatchlistItem> findWatchlistItemByWatchlistId(UUID watchlistId);
}
