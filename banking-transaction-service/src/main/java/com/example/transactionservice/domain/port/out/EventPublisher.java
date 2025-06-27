package com.example.transactionservice.domain.port.out;

import com.example.transactionservice.domain.enums.TransactionStatus;

public interface EventPublisher {
    void publishTransactionApprovedEvent(String transactionId, String authorizationCode);
    void publishTransactionRejectedEvent(String transactionId, String rejectionReason);
    void publishTransactionFailedEvent(String transactionId, String failureReason);
    void publishTransactionSuccessEvent(String transactionId);
    void publishGenericTransactionStatusChangedEvent(String transactionId, TransactionStatus newStatus, String message);
}