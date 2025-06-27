package com.example.bankinghistoryservice.usecase;

import com.example.bankinghistoryservice.domain.enums.TransactionStatus;
import com.example.bankinghistoryservice.domain.model.Transaction;
import com.example.bankinghistoryservice.domain.service.TransactionService;
import com.example.bankinghistoryservice.infrastructure.dto.TransactionDTO;
import com.example.bankinghistoryservice.infrastructure.dto.UpdateTransactionStatusRequest;
import com.example.bankinghistoryservice.infrastructure.exception.InvalidTransactionException;
import com.example.bankinghistoryservice.infrastructure.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;

public class UpdateTransactionStatusUseCase {

    private final TransactionService transactionService;

    public UpdateTransactionStatusUseCase(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Transactional
    public TransactionDTO execute(
            String transactionId,
            UpdateTransactionStatusRequest request) {

        Transaction transaction = transactionService.findById(transactionId);
        if (transaction == null) {
                throw new NotFoundException("Transaction not found with ID: " + transactionId);

        }

        TransactionStatus newStatus = request.getNewStatus();
        TransactionStatus currentStatus = transaction.getStatus();

        if (currentStatus == newStatus) {
            System.out.println("Transaction " + transactionId + " is already in status: " + newStatus);
            return TransactionDTO.fromDomain(transaction);
        }

        switch (currentStatus) {
            case PENDING:
            case PROCESSING:
                if (!EnumSet.of(TransactionStatus.COMPLETED, TransactionStatus.FAILED, TransactionStatus.CANCELLED).contains(newStatus)) {
                    throw new InvalidTransactionException("Invalid status transition from " + currentStatus + " to " + newStatus);
                }
                break;
            case COMPLETED:
                if (!EnumSet.of(TransactionStatus.REFUNDED).contains(newStatus)) {
                    throw new InvalidTransactionException("Invalid status transition from " + currentStatus + " to " + newStatus);
                }
                break;
            case FAILED:
            case CANCELLED:
            case REFUNDED:
                throw new InvalidTransactionException("Cannot change status from terminal state: " + currentStatus);
            default:
                // Handle unknown states or allow all transitions (less safe)
                break;
        }

        transaction.setStatus(newStatus);
        transaction.setFailureReason(request.getFailureReason());

        Transaction updatedTransaction = transactionService.createTransaction(transaction);
        return TransactionDTO.fromDomain(updatedTransaction);

    }
}