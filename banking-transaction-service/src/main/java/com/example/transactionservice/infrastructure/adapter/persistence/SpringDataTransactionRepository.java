package com.example.transactionservice.infrastructure.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataTransactionRepository extends JpaRepository<TransactionEntity, String> {
    // Métodos CRUD básicos já fornecidos pelo JpaRepository
}