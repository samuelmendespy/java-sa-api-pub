package com.example.coreservice.application.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class AccountBalanceUpdateRequest {
    private String accountId;
    private BigDecimal amount;
    private String operation;
}