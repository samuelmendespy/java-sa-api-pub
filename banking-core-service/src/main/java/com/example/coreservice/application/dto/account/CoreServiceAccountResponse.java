package com.example.coreservice.application.dto.account;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CoreServiceAccountResponse {
    private Long userId;
    private String name;
    private Long accountId;
    private String accountNumber;
    private String accountAgency;
    private BigDecimal accountBalance;
}