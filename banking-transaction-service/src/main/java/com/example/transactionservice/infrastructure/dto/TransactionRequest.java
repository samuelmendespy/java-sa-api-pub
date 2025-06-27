package com.example.transactionservice.infrastructure.dto;

import com.example.transactionservice.domain.enums.PayMethodType;
import com.example.transactionservice.domain.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class TransactionRequest {
    @NotNull(message = "Account ID cannot be null")
    private String accountId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Transaction type cannot be null")
    private TransactionType type;

    @NotNull(message = "Payment method type cannot be null")
    private PayMethodType payMethodType;

    private String description;

    // Campos específicos para débito de cartão, se aplicável
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String securityCode;
    // ... outros campos para PIX, Boleto, Transferência
}