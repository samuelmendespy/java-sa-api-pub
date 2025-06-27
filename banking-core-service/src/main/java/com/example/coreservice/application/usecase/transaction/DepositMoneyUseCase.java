package com.example.coreservice.application.usecase.transaction;

import com.example.coreservice.application.dto.account.AccountBalanceRequest;
import com.example.coreservice.application.dto.account.AccountBalanceUpdateRequest;
import com.example.coreservice.application.dto.transaction.CoreServiceTransactionResponse;
import com.example.coreservice.application.dto.transaction.DepositRequest;
import com.example.coreservice.application.dto.transaction.TransactionRecordRequest;
import com.example.coreservice.application.port.TransactionServicePort;
import com.example.coreservice.application.port.AccountServicePort;
import com.example.coreservice.domain.enums.TransactionType;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class DepositMoneyUseCase {

    private final AccountServicePort accountServicePort;
    private final TransactionServicePort transactionServicePort;

    public DepositMoneyUseCase(AccountServicePort accountServicePort, TransactionServicePort transactionServicePort) {
        this.accountServicePort = accountServicePort;
        this.transactionServicePort = transactionServicePort;
    }

    public Mono<CoreServiceTransactionResponse> execute(DepositRequest request) {
        if (request.getType() != TransactionType.CREDIT) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deposit must be a CREDIT transaction type."));
        }

        AccountBalanceRequest balanceRequest = new AccountBalanceRequest(request.getAccountId());

        return accountServicePort.getAccountBalance(balanceRequest)
                .flatMap(balanceResponse -> {
                    AccountBalanceUpdateRequest balanceUpdateRequest = new AccountBalanceUpdateRequest(
                            request.getAccountId(),
                            request.getAmount(),
                            "CREDIT");
                    return accountServicePort.updateAccountBalance(balanceUpdateRequest);
                })
                .flatMap(updatedBalance -> {
                    TransactionRecordRequest transactionRecordRequest = new TransactionRecordRequest();
                    transactionRecordRequest.setType(TransactionType.CREDIT);
                    transactionRecordRequest.setAmount(request.getAmount());
                    transactionRecordRequest.setDescription("Money Deposit");
                    transactionRecordRequest.setAccountId(request.getAccountId());
                    transactionRecordRequest.setRecipientInfo("Self-Deposit");
                    return transactionServicePort.recordTransaction(transactionRecordRequest);
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.")));
    }
}