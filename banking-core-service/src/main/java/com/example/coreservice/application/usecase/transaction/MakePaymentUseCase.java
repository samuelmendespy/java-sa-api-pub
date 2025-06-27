package com.example.coreservice.application.usecase.transaction;

import com.example.coreservice.application.dto.account.AccountBalanceRequest;
import com.example.coreservice.application.dto.account.AccountBalanceUpdateRequest;
import com.example.coreservice.application.dto.transaction.CoreServiceTransactionResponse;
import com.example.coreservice.application.dto.transaction.PaymentRequest;
import com.example.coreservice.application.dto.transaction.TransactionRecordRequest;
import com.example.coreservice.application.port.TransactionServicePort;
import com.example.coreservice.application.port.AccountServicePort;
import com.example.coreservice.domain.enums.TransactionType;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class MakePaymentUseCase {

    private final AccountServicePort accountServicePort;
    private final TransactionServicePort transactionServicePort;

    public MakePaymentUseCase(AccountServicePort accountServicePort, TransactionServicePort transactionServicePort) {
        this.accountServicePort = accountServicePort;
        this.transactionServicePort = transactionServicePort;
    }

    public Mono<CoreServiceTransactionResponse> execute(PaymentRequest request) {
        if (request.getType() != TransactionType.DEBIT) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment must be a DEBIT transaction type."));
        }

        AccountBalanceRequest balanceRequest = new AccountBalanceRequest(
                request.getSenderAccountId()
        );

        return accountServicePort.getAccountBalance(balanceRequest)
                .flatMap(balanceResponse -> {
                    if (balanceResponse.getCurrentBalance().compareTo(request.getAmount()) < 0) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance for payment."));
                    }

                    // Subtract amount from user's balance
                    AccountBalanceUpdateRequest balanceUpdateRequest = new AccountBalanceUpdateRequest(
                            request.getSenderAccountId(),
                            request.getAmount(),
                            "DEBIT"
                    );

                    return accountServicePort.updateAccountBalance(balanceUpdateRequest);
                })
                .flatMap(updatedBalance -> {
                    // Record the transaction
                    TransactionRecordRequest transactionRecordRequest = new TransactionRecordRequest();
                    transactionRecordRequest.setType(TransactionType.DEBIT);
                    transactionRecordRequest.setAmount(request.getAmount());
                    transactionRecordRequest.setDescription("Payment to " + request.getRecipientInfo());
                    transactionRecordRequest.setAccountId(request.getSenderAccountId());
                    transactionRecordRequest.setRecipientInfo(request.getRecipientInfo());
                    return transactionServicePort.recordTransaction(transactionRecordRequest);
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.")));
    }
}