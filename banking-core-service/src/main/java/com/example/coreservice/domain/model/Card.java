package com.example.coreservice.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Card {
    private String id;
    private String number;
    private BigDecimal creditLimit;
}