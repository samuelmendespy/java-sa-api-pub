package com.example.coreservice.application.usecase.transaction;

import com.example.coreservice.application.dto.account.AccountBalanceRequest;
import com.example.coreservice.application.dto.account.AccountBalanceUpdateRequest;
import com.example.coreservice.application.dto.transaction.CoreServiceTransactionResponse;
import com.example.coreservice.application.dto.transaction.TransactionRecordRequest;
import com.example.coreservice.application.dto.transaction.WithdrawRequest;
import com.example.coreservice.application.port.TransactionServicePort;
import com.example.coreservice.application.port.AccountServicePort;
import com.example.coreservice.domain.enums.TransactionType;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class WithdrawMoneyUseCase {

    private final AccountServicePort accountServicePort;
    private final TransactionServicePort transactionServicePort;

    public WithdrawMoneyUseCase(AccountServicePort accountServicePort, TransactionServicePort transactionServicePort) {
        this.accountServicePort = accountServicePort;
        this.transactionServicePort = transactionServicePort;
    }

    public Mono<CoreServiceTransactionResponse> execute(WithdrawRequest request) {
        if (request.getType() != TransactionType.DEBIT) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Withdrawal must be a DEBIT transaction type."));
        }

        AccountBalanceRequest balanceRequest = new AccountBalanceRequest(
                request.getAccountId()
        );
        return accountServicePort.getAccountBalance(balanceRequest)
                .flatMap(balanceResponse -> {
                    if (balanceResponse.getCurrentBalance().compareTo(request.getAmount()) < 0) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance for withdrawal."));
                    }
                    // Subtract amount from user's balance
                    AccountBalanceUpdateRequest balanceUpdateRequest = new AccountBalanceUpdateRequest(
                            request.getAccountId(),
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
                    transactionRecordRequest.setDescription("Money Withdrawal");
                    transactionRecordRequest.setAccountId(request.getAccountId());
                    transactionRecordRequest.setRecipientInfo("Self-Withdrawal");
                    return transactionServicePort.recordTransaction(transactionRecordRequest);
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.")));
    }
}