package com.example.transactionservice.domain.enums;

public enum TransactionStatus {
    PENDING,
    APPROVED,
    REJECTED,
    FAILED,
    SUCCESS;

    public boolean isFinalStatus() {
        return this == REJECTED || this == FAILED || this == SUCCESS;
    }
}