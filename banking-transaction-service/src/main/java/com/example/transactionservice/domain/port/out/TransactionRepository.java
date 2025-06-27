package com.example.transactionservice.domain.port.out;

import com.example.transactionservice.domain.model.Transaction;

import java.util.Optional;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(String id);
    // Adicione outros métodos de busca conforme necessário
}