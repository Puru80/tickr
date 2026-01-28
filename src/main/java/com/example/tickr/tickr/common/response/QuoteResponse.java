package com.example.tickr.tickr.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteResponse {

    private String instrumentName;
    private String exchange;
    private double lastPrice;
    private double high;
    private double low;
    private double open;
    private double close;
}
