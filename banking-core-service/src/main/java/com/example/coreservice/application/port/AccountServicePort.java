package com.example.coreservice.application.port;

import com.example.coreservice.application.dto.account.*;
import reactor.core.publisher.Mono;

public interface AccountServicePort {
    Mono<CoreServiceAccountResponse> createBankAccount(AccountCreationRequest request);
    Mono<CoreServiceAccountResponse> getAccount(AccountSelectionByIdRequest request);
    Mono<AccountUpdateResponse> updateAccount(AccountUpdateRequest request);
    Mono<AccountBalanceResponse> getAccountBalance(AccountBalanceRequest request);
    Mono<AccountBalanceResponse> updateAccountBalance(AccountBalanceUpdateRequest request);
}