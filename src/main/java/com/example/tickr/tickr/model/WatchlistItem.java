package com.example.tickr.tickr.model;

import com.example.tickr.tickr.model.enums.ReferenceType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
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
    private String tradingSymbol;

    @NonNull
    private String exchange;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;
    private Double referencePrice;
    private Double lastPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WatchlistItem that = (WatchlistItem) o;
        return Objects.equals(id, that.id) && Objects.equals(watchlistId, that.watchlistId) && Objects.equals(tradingSymbol, that.tradingSymbol) && Objects.equals(exchange, that.exchange) && referenceType == that.referenceType && Objects.equals(referencePrice, that.referencePrice) && Objects.equals(lastPrice, that.lastPrice) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, watchlistId, tradingSymbol, exchange, referenceType, referencePrice, lastPrice, createdAt, updatedAt);
    }
}
