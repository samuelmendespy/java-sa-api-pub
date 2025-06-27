package com.example.transactionservice.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProcessBoletoDepositCommand {
    private String transactionId; // Pode ser gerado antes ou dentro do use case
    private String accountId;
    private BigDecimal amount;
    private String boletoNumber;
    private String expirationDate;
}