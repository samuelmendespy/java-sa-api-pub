package com.example.transactionservice.infrastructure.adapter.event;

import com.example.transactionservice.domain.enums.TransactionStatus;
import com.example.transactionservice.domain.port.out.EventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LocalEventPublisherAdapter implements EventPublisher {

    @Override
    public void publishTransactionApprovedEvent(String transactionId, String authorizationCode) {
        System.out.println("EVENT: TransactionApprovedEvent - Transaction ID: " + transactionId + ", Auth Code: " + authorizationCode);
    }

    @Override
    public void publishTransactionRejectedEvent(String transactionId, String rejectionReason) {
        System.out.println("EVENT: TransactionRejectedEvent - Transaction ID: " + transactionId + ", Reason: " + rejectionReason);
    }

    @Override
    public void publishTransactionFailedEvent(String transactionId, String failureReason) {
        System.out.println("EVENT: TransactionFailedEvent - Transaction ID: " + transactionId + ", Reason: " + failureReason);
    }

    @Override
    public void publishTransactionSuccessEvent(String transactionId) {
        System.out.println("EVENT: TransactionSuccessEvent - Transaction ID: " + transactionId + ", Status: SUCCESS");
    }

    @Override
    public void publishGenericTransactionStatusChangedEvent(String transactionId, TransactionStatus newStatus, String message) {
        System.out.println("EVENT: TransactionStatusChangedEvent - Transaction ID: " + transactionId + ", New Status: " + newStatus + ", Message: " + message);
    }
}