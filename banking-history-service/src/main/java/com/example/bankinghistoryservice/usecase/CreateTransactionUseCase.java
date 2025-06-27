package com.example.bankinghistoryservice.usecase;

import com.example.bankinghistoryservice.domain.model.Transaction;
import com.example.bankinghistoryservice.domain.service.TransactionService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateTransactionUseCase {

    private final TransactionService transactionService;

    public CreateTransactionUseCase(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Transactional
    public Transaction execute(
            Transaction transaction
    ) {
        Transaction existingTransaction = transactionService.findByIdempotencyKey(transaction.getIdempotencyKey());
        if (existingTransaction != null) {
            // TODO: Add a log system
            // "Idempotency key found. Returning existing transaction for key: " + transaction.getIdempotencyKey());
            return existingTransaction;
        }

        return transactionService.createTransaction(transaction);
    }
}
