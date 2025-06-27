package com.example.coreservice.application.dto.transaction;

import lombok.Getter;
import lombok.Setter;
import com.example.coreservice.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CoreServiceTransactionResponse {
    private Long transactionId;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private LocalDateTime timestamp;
    private Long accountId;
    private String recipientInfo;
}