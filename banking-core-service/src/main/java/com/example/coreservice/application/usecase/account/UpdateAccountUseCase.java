package com.example.coreservice.application.usecase.account;

import com.example.coreservice.application.dto.account.AccountUpdateRequest;
import com.example.coreservice.application.dto.account.AccountUpdateResponse;
import com.example.coreservice.application.port.AccountServicePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateAccountUseCase {

    private final AccountServicePort accountServicePort;

    public UpdateAccountUseCase(AccountServicePort accountServicePort) {
        this.accountServicePort = accountServicePort;
    }

    public Mono<AccountUpdateResponse> execute(AccountUpdateRequest request) {
        return accountServicePort.updateAccount(request);
    }
}