package com.example.transactionservice.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class BankAuthorizationResponse {
    private boolean approved;
    private String authorizationCode;
    private String rejectionReason;
    private String transactionId; // O ID da transação interna que foi enviado
    private String bankTransactionId; // ID da transação gerado pelo sistema bancário externo

    public static BankAuthorizationResponse approved(String transactionId, String authorizationCode, String bankTransactionId) {
        return BankAuthorizationResponse.builder()
                .approved(true)
                .transactionId(transactionId)
                .authorizationCode(authorizationCode)
                .bankTransactionId(bankTransactionId)
                .build();
    }

    public static BankAuthorizationResponse rejected(String rejectionReason, String transactionId) {
        return BankAuthorizationResponse.builder()
                .approved(false)
                .transactionId(transactionId)
                .rejectionReason(rejectionReason)
                .build();
    }
}