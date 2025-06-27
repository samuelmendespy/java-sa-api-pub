package com.example.transactionservice.application.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionProcessingResult {
    private String transactionId;
    private boolean success;
    private String message;
    private String authorizationCode;
    private String bankTransactionId;
    private String rejectionReason;

    public static TransactionProcessingResult success(String transactionId, String authorizationCode, String bankTransactionId) {
        return TransactionProcessingResult.builder()
                .transactionId(transactionId)
                .success(true)
                .message("Transaction processed successfully.")
                .authorizationCode(authorizationCode)
                .bankTransactionId(bankTransactionId)
                .build();
    }

    public static TransactionProcessingResult rejected(String transactionId, String rejectionReason) {
        return TransactionProcessingResult.builder()
                .transactionId(transactionId)
                .success(false)
                .message("Transaction rejected.")
                .rejectionReason(rejectionReason)
                .build();
    }

    public static TransactionProcessingResult failed(String transactionId, String errorMessage) {
        return TransactionProcessingResult.builder()
                .transactionId(transactionId)
                .success(false)
                .message("Transaction failed: " + errorMessage)
                .rejectionReason(errorMessage) // Ou um campo específico para falha
                .build();
    }
}