package com.example.tickr.tickr.model;

import com.example.tickr.tickr.model.enums.InstrumentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "instruments_info")
public class InstrumentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public String isin;
    public String tradingSymbol;
    public String name;
    public double lastPrice;
    public String exchange;

    @Enumerated(EnumType.STRING)
    public InstrumentType instrumentType;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InstrumentInfo that = (InstrumentInfo) o;
        return Double.compare(lastPrice, that.lastPrice) == 0 && Objects.equals(id, that.id) && Objects.equals(isin, that.isin) && Objects.equals(tradingSymbol, that.tradingSymbol) && Objects.equals(name, that.name) && Objects.equals(exchange, that.exchange) && instrumentType == that.instrumentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isin, tradingSymbol, name, lastPrice, exchange, instrumentType);
    }
}
