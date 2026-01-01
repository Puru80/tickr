package com.example.tickr.tickr.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "watchlist_item")
public class WatchlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    private UUID watchlistId;

    @NonNull
    private String symbol;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WatchlistItem that = (WatchlistItem) o;
        return java.util.Objects.equals(id, that.id) && java.util.Objects.equals(watchlistId, that.watchlistId) && java.util.Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, watchlistId, symbol);
    }
}
