package com.example.transactionservice.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProcessTransferPaymentCommand {
    private String transactionId;
    private String accountId;
    private String recipientInfo;
    private BigDecimal amount;
    private String description;
    private String expirationDate;
}