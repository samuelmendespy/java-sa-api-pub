package com.example.coreservice.application.usecase.account;

import com.example.coreservice.application.dto.account.AccountSelectionByIdRequest;
import com.example.coreservice.application.dto.account.CoreServiceAccountResponse;
import com.example.coreservice.application.port.AccountServicePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetAccountByIdUseCase {

    private final AccountServicePort accountServicePort;

    public GetAccountByIdUseCase(AccountServicePort accountServicePort) {
        this.accountServicePort = accountServicePort;
    }

    public Mono<CoreServiceAccountResponse> execute(AccountSelectionByIdRequest request) {
        return accountServicePort.getAccount(request);
    }
}