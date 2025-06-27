package com.example.coreservice.application.usecase.account;

import com.example.coreservice.application.dto.account.AccountBalanceRequest;
import com.example.coreservice.application.dto.account.AccountBalanceResponse;
import com.example.coreservice.application.port.AccountServicePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetAccountBalanceUseCase {

    private final AccountServicePort accountServicePort;

    public GetAccountBalanceUseCase(AccountServicePort accountServicePort) {
        this.accountServicePort = accountServicePort;
    }

    public Mono<AccountBalanceResponse> execute(AccountBalanceRequest request) {
        return accountServicePort.getAccountBalance(request);
    }
}