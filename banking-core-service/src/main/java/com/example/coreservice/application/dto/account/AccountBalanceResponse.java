package com.example.coreservice.application.dto.account;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountBalanceResponse {
    private String accountId;
    private BigDecimal currentBalance;
}