package com.example.transactionservice.domain.model;

import com.example.transactionservice.domain.enums.PayMethodType;
import com.example.transactionservice.domain.enums.TransactionStatus;
import com.example.transactionservice.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "tb_transaction")
@Getter
@Setter
@ToString
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String accountId;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayMethodType payMethodType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private String description;

    private String bankTransactionId;
    private String authorizationCode;
    private String rejectionReason;


    private Transaction(String id, String accountId, BigDecimal amount, TransactionType type, PayMethodType payMethodType, TransactionStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, String description, String bankTransactionId, String authorizationCode, String rejectionReason) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.payMethodType = payMethodType;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.description = description;
        this.bankTransactionId = bankTransactionId;
        this.authorizationCode = authorizationCode;
        this.rejectionReason = rejectionReason;
    }

    public static Transaction newPendingTransaction(String accountId, BigDecimal amount, TransactionType type, PayMethodType payMethodType, String description) {
        String newTransactionId = UUID.randomUUID().toString();
        return Transaction.builder()
                .id(newTransactionId)
                .accountId(accountId)
                .amount(amount)
                .type(type)
                .payMethodType(payMethodType)
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .description(description)
                .build();
    }

    public void approve(String authorizationCode, String bankTransactionId) {
        if (this.status != TransactionStatus.PENDING) {
            throw new IllegalStateException("Transaction cannot be approved from status: " + this.status);
        }
        this.status = TransactionStatus.APPROVED;
        this.authorizationCode = authorizationCode;
        this.bankTransactionId = bankTransactionId;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsSuccess() {
        if (this.status != TransactionStatus.APPROVED) {
            throw new IllegalStateException("Transaction cannot be marked as success from status: " + this.status);
        }
        this.status = TransactionStatus.SUCCESS;
        this.updatedAt = LocalDateTime.now();
    }

    public void reject(String reason) {
        if (this.status.isFinalStatus()) {
            throw new IllegalStateException("Transaction cannot be rejected from final status: " + this.status);
        }
        this.status = TransactionStatus.REJECTED;
        this.rejectionReason = reason;
        this.updatedAt = LocalDateTime.now();
    }

    public void fail(String reason) {
        if (this.status.isFinalStatus()) {
            throw new IllegalStateException("Transaction cannot be failed from final status: " + this.status);
        }
        this.status = TransactionStatus.FAILED;
        this.rejectionReason = reason;
        this.updatedAt = LocalDateTime.now();
    }
}