package com.example.transactionservice.infrastructure.controller;

import com.example.transactionservice.application.command.ProcessDebitCardPaymentCommand;
import com.example.transactionservice.application.result.TransactionProcessingResult;
import com.example.transactionservice.application.usecase.ProcessDebitCardPaymentTransactionUseCase;
import com.example.transactionservice.infrastructure.dto.TransactionRequest;
import com.example.transactionservice.infrastructure.dto.TransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ProcessDebitCardPaymentTransactionUseCase processDebitCardPaymentTransactionUseCase;
    // injetar outros use cases conforme forem implementados

    @PostMapping("/debit-card-payment")
    public ResponseEntity<TransactionResponse> processDebitCardPayment(@Valid @RequestBody TransactionRequest request) {
        // Mapear DTO de requisição para o comando do Use Case
        ProcessDebitCardPaymentCommand command = ProcessDebitCardPaymentCommand.builder()
                .accountId(request.getAccountId())
                .amount(request.getAmount())
                .description(request.getDescription())
                .cardNumber(request.getCardNumber())
                .cardHolderName(request.getCardHolderName())
                .expirationDate(request.getExpirationDate())
                .securityCode(request.getSecurityCode())
                .build();

        TransactionProcessingResult result = processDebitCardPaymentTransactionUseCase.execute(command);

        // Mapear o resultado do Use Case para o DTO de resposta
        TransactionResponse response = TransactionResponse.builder()
                .id(result.getTransactionId())
                .status(result.isSuccess() ? (result.getAuthorizationCode() != null ? com.example.transactionservice.domain.enums.TransactionStatus.SUCCESS : com.example.transactionservice.domain.enums.TransactionStatus.REJECTED) : com.example.transactionservice.domain.enums.TransactionStatus.FAILED) // Ajustar o status baseado no resultado real do use case
                .description(result.getMessage())
                .authorizationCode(result.getAuthorizationCode())
                .bankTransactionId(result.getBankTransactionId())
                .rejectionReason(result.getRejectionReason())
                .build();

        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response); // Ou HttpStatus.CREATED
        } else {
            // Em caso de rejeição ou falha, retornar um status HTTP apropriado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Ou HttpStatus.UNPROCESSABLE_ENTITY
        }
    }

    // Adicionar outros endpoints para os demais casos de uso
}