package com.example.coreservice.application.dto.transaction;

import lombok.Getter;
import lombok.Setter;
import com.example.coreservice.domain.enums.TransactionType;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionRecordRequest {
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private String accountId;
    private String recipientInfo;
}