package com.example.coreservice.infrastructure.adapter;

import com.example.coreservice.application.dto.transaction.CoreServiceTransactionResponse;
import com.example.coreservice.application.dto.transaction.TransactionRecordRequest;
import com.example.coreservice.application.port.TransactionServicePort;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
public class TransactionServiceClient implements TransactionServicePort {

    private final WebClient webClient;

    public TransactionServiceClient(@Value("${TRANSACTION_SERVICE_URL}") String transactionServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(transactionServiceUrl).build();
    }

    @Override
    public Mono<CoreServiceTransactionResponse> recordTransaction(TransactionRecordRequest request) {
        return webClient.post()
                .uri("/transactions")
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(response.statusCode(), error))))
                .bodyToMono(CoreServiceTransactionResponse.class);

    }
}