package com.example.bankinghistoryservice.domain.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.bankinghistoryservice.domain.enums.TransactionType;
import com.example.bankinghistoryservice.domain.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "tb_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String senderAccountId;

    private String recipientInfo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String failureReason;

    @Column(unique = true)
    private String idempotencyKey;

}