package com.example.tickr.tickr.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "market_holidays")
public class MarketHoliday {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String exchange;
    private String holidayDate;
    private String description;

}
