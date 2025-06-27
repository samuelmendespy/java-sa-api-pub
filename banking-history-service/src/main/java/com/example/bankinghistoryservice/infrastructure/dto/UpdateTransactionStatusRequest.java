package com.example.bankinghistoryservice.infrastructure.dto;

import com.example.bankinghistoryservice.domain.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTransactionStatusRequest {

    @NotNull(message = "New transaction status is required")
    private TransactionStatus newStatus;

    private String failureReason;
}