package com.example.coreservice.infrastructure.adapter;

import com.example.coreservice.application.dto.account.*;
import com.example.coreservice.application.port.AccountServicePort;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
public class AccountServiceClient implements AccountServicePort {

    private final WebClient webClient;

    public AccountServiceClient(@Value("${USER_SERVICE_URL}") String userServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(userServiceUrl).build();
    }

    @Override
    public Mono<CoreServiceAccountResponse> createBankAccount(AccountCreationRequest request) {
        return webClient.post()
                .uri("/accounts")
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(response.statusCode(), error))))
                .bodyToMono(CoreServiceAccountResponse.class);
    }

    @Override
    public Mono<CoreServiceAccountResponse> getAccount(AccountSelectionByIdRequest request) {
        return webClient.get()
                .uri("/accounts/{accountId}", request.getId())
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(response.statusCode(), error))))
                .bodyToMono(CoreServiceAccountResponse.class);
    }

    @Override
    public Mono<AccountUpdateResponse> updateAccount(AccountUpdateRequest request) {
        return webClient.patch()
                .uri("/accounts/{accountId}/balance", request.getAccountId())
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(response.statusCode(), error))))
                .bodyToMono(AccountUpdateResponse.class);
    }

    @Override
    public Mono<AccountBalanceResponse> getAccountBalance(AccountBalanceRequest request) {
        return webClient.get()
                .uri("/accounts/{accountId}/balance", request.getAccountId())
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(response.statusCode(), error))))
                .bodyToMono(AccountBalanceResponse.class);
    }

    @Override
    public Mono<AccountBalanceResponse> updateAccountBalance(AccountBalanceUpdateRequest request) {
        return webClient.patch()
                .uri("/accounts/{accountId}/balance", request.getAccountId())
                .bodyValue(request)
                .retrieve()
                .onStatus(
                HttpStatusCode::is4xxClientError,
                response -> response.bodyToMono(String.class)
                        .flatMap(error -> Mono.error(new ResponseStatusException(response.statusCode(), error))))
                .bodyToMono(AccountBalanceResponse.class);
    }
}
