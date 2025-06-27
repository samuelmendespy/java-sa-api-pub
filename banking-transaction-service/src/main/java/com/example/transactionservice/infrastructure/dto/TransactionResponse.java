package com.example.transactionservice.infrastructure.dto;

import com.example.transactionservice.domain.enums.PayMethodType;
import com.example.transactionservice.domain.enums.TransactionStatus;
import com.example.transactionservice.domain.enums.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class TransactionResponse {
    private String id;
    private String accountId;
    private BigDecimal amount;
    private TransactionType type;
    private PayMethodType payMethodType;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String description;
    private String bankTransactionId;
    private String authorizationCode;
    private String rejectionReason;
}