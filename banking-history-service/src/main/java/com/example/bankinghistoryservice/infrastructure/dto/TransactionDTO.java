package com.example.bankinghistoryservice.infrastructure.dto;

import com.example.bankinghistoryservice.domain.enums.TransactionStatus;
import com.example.bankinghistoryservice.domain.enums.TransactionType;
import com.example.bankinghistoryservice.domain.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    private String id;
    private String senderAccountId;
    private String recipientInfo;
    private TransactionType type;
    private BigDecimal amount;
    private TransactionStatus status;
    private LocalDateTime timestamp;
    private String description;
    private String failureReason;
    private String idempotencyKey;

    public static TransactionDTO fromDomain(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setSenderAccountId(transaction.getSenderAccountId());
        dto.setRecipientInfo(transaction.getRecipientInfo());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setStatus(transaction.getStatus());
        dto.setTimestamp(transaction.getTimestamp());
        dto.setDescription(transaction.getDescription());
        dto.setFailureReason(transaction.getFailureReason());

        return dto;
    }

    public Transaction toDomain() {
        Transaction transaction = new Transaction();
        transaction.setId(this.id);
        transaction.setSenderAccountId(this.senderAccountId);
        transaction.setRecipientInfo(this.recipientInfo);
        transaction.setType(this.type);
        transaction.setAmount(this.amount);
        transaction.setStatus(this.status);
        transaction.setTimestamp(this.timestamp);
        transaction.setDescription(this.description);
        transaction.setFailureReason(this.failureReason);
        transaction.setIdempotencyKey(this.idempotencyKey);
        return transaction;
    }
}