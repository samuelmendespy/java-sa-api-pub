package com.example.coreservice.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {
    private String id;
    private String number;
    private String agency;
    private BigDecimal balance;
    private BigDecimal overdraftLimit;
    private List<Card> cards;
}
