package com.example.bankinghistoryservice.usecase;

import com.example.bankinghistoryservice.domain.model.Transaction;
import com.example.bankinghistoryservice.domain.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


public class GetTransactionsByAccountIdUseCase {

    private final TransactionService transactionService;

    public GetTransactionsByAccountIdUseCase(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Transactional
    public Page<Transaction> execute(
            String accountId,
            Pageable pageable
    ) {
        Page<Transaction> existingTransaction = transactionService.getTransactionsBySenderAccountId(accountId, pageable);
        return  existingTransaction;
    }
}