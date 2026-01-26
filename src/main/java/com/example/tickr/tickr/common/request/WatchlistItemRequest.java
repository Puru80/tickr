package com.example.tickr.tickr.common.request;

import com.example.tickr.tickr.model.enums.ReferenceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistItemRequest {
    private String tradingSymbol;
    private String exchange;
    private ReferenceType referenceType;
    private double referencePrice;
}
