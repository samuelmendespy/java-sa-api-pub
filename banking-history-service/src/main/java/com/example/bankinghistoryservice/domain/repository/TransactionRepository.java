package com.example.bankinghistoryservice.domain.repository;

import com.example.bankinghistoryservice.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    // Custom query to find transactions where account is either source or destination
    Page<Transaction> findBySenderAccountId(String sourceAccountId, Pageable pageable);

    // For idempotency check
    Optional<Transaction> findByIdempotencyKey(String idempotencyKey);
}
