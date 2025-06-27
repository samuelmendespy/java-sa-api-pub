package com.example.transactionservice.infrastructure.adapter.external;

import com.example.transactionservice.domain.port.out.BankGateway;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Setter // Para testes, se necessário injetar o webClient
public class BankHttpClientAdapter implements BankGateway {

    @Value("${bank.api.base-url}")
    private String bankBaseUrl;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(bankBaseUrl)
                .build();
    }

    // Construtor para testes com WebClient Mock
    public BankHttpClientAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    public BankHttpClientAdapter() {
        // Construtor padrão para o Spring
    }


    @Override
    public BankAuthorizationResponse authorizeAndDebit(BankAuthorizationRequest request) {
        String endpoint = "/api/v1/authorizations";

        try {
            BankAuthorizationResponse bankResponse = webClient.post()
                    .uri(endpoint)
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .map(body -> {
                                        System.err.println("Bank System Error Response: " + body);
                                        return new WebClientResponseException(
                                                "Bank System returned error status " + response.statusCode(),
                                                response.statusCode().value(),
                                                response.statusCode().getReasonPhrase(),
                                                response.headers().asHttpHeaders(),
                                                body.getBytes(),
                                                null
                                        );
                                    }))
                    .bodyToMono(BankAuthorizationResponse.class)
                    .block();

            if (bankResponse == null) {
                return BankAuthorizationResponse.rejected("UNKNOWN_BANK_ERROR: Empty or malformed response", request.getTransactionId());
            }
            return bankResponse;

        } catch (WebClientResponseException e) {
            System.err.println("HTTP Error communicating with Bank System: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return BankAuthorizationResponse.rejected("BANK_SYSTEM_ERROR: " + e.getStatusText() + " - " + e.getResponseBodyAsString(), request.getTransactionId());
        } catch (Exception e) {
            System.err.println("Error communicating with Bank System for authorization: " + e.getMessage());
            return BankAuthorizationResponse.rejected("BANK_UNAVAILABLE: " + e.getMessage(), request.getTransactionId());
        }
    }
}