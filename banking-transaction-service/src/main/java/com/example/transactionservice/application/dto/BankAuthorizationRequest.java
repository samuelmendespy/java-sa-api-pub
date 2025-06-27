package com.example.transactionservice.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
public class BankAuthorizationRequest {
    private String transactionId; // ID da transação interna
    private BigDecimal amount;
    private String paymentMethod; // Ex: "DEBIT_CARD", "PIX"
    private String accountId; // ID da conta no banco externo ou interno se for o mesmo
    private String description;
    private String cardNumber;
    private String expirationDate;
    private String securityCode;
    private String boletoNumber;
    private String recipientInfo;
}