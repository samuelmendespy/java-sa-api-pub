package com.example.coreservice.application.dto.account;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountCreationRequest {
    private String name;
    private String accountNumber;
    private String accountAgency;
    private BigDecimal initialBalance;
}