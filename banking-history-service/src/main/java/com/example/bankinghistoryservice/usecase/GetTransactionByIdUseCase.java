package com.example.bankinghistoryservice.usecase;

import com.example.bankinghistoryservice.domain.model.Transaction;
import com.example.bankinghistoryservice.domain.service.TransactionService;
import com.example.bankinghistoryservice.infrastructure.dto.TransactionDTO;
import com.example.bankinghistoryservice.infrastructure.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class GetTransactionByIdUseCase {

    private final TransactionService transactionService;

    public GetTransactionByIdUseCase(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Transactional
    public TransactionDTO execute(String transactionId) {
        Transaction transaction = transactionService.findById(transactionId);
        if (transaction == null) {
                throw new NotFoundException("Transaction not found with ID: " + transactionId);
        }

        return TransactionDTO.fromDomain(transaction);
    }
}
