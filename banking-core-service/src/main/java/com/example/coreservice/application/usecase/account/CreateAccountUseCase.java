package com.example.coreservice.application.usecase.account;

import com.example.coreservice.application.dto.account.CoreServiceAccountResponse;
import com.example.coreservice.application.dto.account.AccountCreationRequest;
import com.example.coreservice.application.port.AccountServicePort;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateAccountUseCase {

    private final AccountServicePort accountServicePort;

    public CreateAccountUseCase(AccountServicePort accountServicePort) {
        this.accountServicePort = accountServicePort;
    }

    public Mono<CoreServiceAccountResponse> execute(AccountCreationRequest request) {
        return accountServicePort.createBankAccount(request);
    }
}