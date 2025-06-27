package com.example.transactionservice.infrastructure.adapter.persistence;

import com.example.transactionservice.domain.enums.PayMethodType;
import com.example.transactionservice.domain.enums.TransactionStatus;
import com.example.transactionservice.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@ToString
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String accountId; // ID da conta envolvida na transação

    @Column(nullable = false)
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

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    private String description;
    private String bankTransactionId; // ID da transação no sistema bancário externo
    private String authorizationCode; // Código de autorização do banco externo
    private String rejectionReason; // Razão da rejeição, se houver
}