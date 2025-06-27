package com.example.bankinghistoryservice.domain.service;

import com.example.bankinghistoryservice.domain.model.Transaction;
import com.example.bankinghistoryservice.domain.repository.TransactionRepository;
import com.example.bankinghistoryservice.infrastructure.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public Transaction findById(String id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Transaction> getTransactionsBySenderAccountId(String id, Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findBySenderAccountId(id, pageable);
        if (transactions.isEmpty()) {
            throw new NotFoundException("Transaction not found with ID: " + id);
        }
        return transactions;
    }

    @Transactional(readOnly = true)
    public Transaction findByIdempotencyKey(String id) {
        return transactionRepository.findByIdempotencyKey(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Transaction> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction updateTransaction(Transaction transaction) {
        if (!transactionRepository.existsById(transaction.getId())) {
            throw new NotFoundException("Transaction not found with ID: " + transaction.getId());
        }
        return transactionRepository.save(transaction);
    }

    @Transactional
    public void deleteTransaction(String id) {
        if (!transactionRepository.existsById(id)) {
            throw new NotFoundException("Transaction not found with ID: " + id);
        }
        transactionRepository.deleteById(id);
    }
}