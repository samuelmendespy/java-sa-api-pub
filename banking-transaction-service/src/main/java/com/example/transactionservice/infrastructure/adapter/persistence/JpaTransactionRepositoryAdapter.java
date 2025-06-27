package com.example.transactionservice.infrastructure.adapter.persistence;

import com.example.transactionservice.domain.model.Transaction;
import com.example.transactionservice.domain.port.out.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaTransactionRepositoryAdapter implements TransactionRepository {

    private final SpringDataTransactionRepository springDataTransactionRepository;

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = toEntity(transaction);
        TransactionEntity savedEntity = springDataTransactionRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Transaction> findById(String id) {
        return springDataTransactionRepository.findById(id).map(this::toDomain);
    }

    // Métodos de conversão entre Domain Model e JPA Entity
    private TransactionEntity toEntity(Transaction domain) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(domain.getId());
        entity.setAccountId(domain.getAccountId());
        entity.setAmount(domain.getAmount());
        entity.setType(domain.getType());
        entity.setPayMethodType(domain.getPayMethodType());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setDescription(domain.getDescription());
        entity.setBankTransactionId(domain.getBankTransactionId());
        entity.setAuthorizationCode(domain.getAuthorizationCode());
        entity.setRejectionReason(domain.getRejectionReason());
        return entity;
    }

    private Transaction toDomain(TransactionEntity entity) {
        return Transaction.builder()
                .id(entity.getId())
                .accountId(entity.getAccountId())
                .amount(entity.getAmount())
                .type(entity.getType())
                .payMethodType(entity.getPayMethodType())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .description(entity.getDescription())
                .bankTransactionId(entity.getBankTransactionId())
                .authorizationCode(entity.getAuthorizationCode())
                .rejectionReason(entity.getRejectionReason())
                .build();
    }
}