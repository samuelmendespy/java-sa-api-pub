package com.example.coreservice.infrastructure.adapter;

import com.example.coreservice.application.dto.user.CoreServiceUserResponse;
import com.example.coreservice.application.dto.user.UserCreationRequest;
import com.example.coreservice.application.dto.user.UserSelectionByIdRequest;
import com.example.coreservice.application.dto.user.UserUpdateRequest;
import com.example.coreservice.application.port.UserServicePort;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
public class UserServiceClient implements UserServicePort {

    private final WebClient webClient;

    public UserServiceClient(@Value("${USER_SERVICE_URL}") String userServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(userServiceUrl).build();
    }

    @Override
    public Mono<CoreServiceUserResponse> createUser(UserCreationRequest request) {
        return webClient.post()
                .uri("/users")
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(response.statusCode(), error))))
                .bodyToMono(CoreServiceUserResponse.class);
    }

    @Override
    public Mono<CoreServiceUserResponse> getUser(UserSelectionByIdRequest request) {
        return webClient.post()
                .uri("/users/" + request.getId())
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(response.statusCode(), error))))
                .bodyToMono(CoreServiceUserResponse.class);
    }

    @Override
    public Mono<CoreServiceUserResponse> updateUser(UserUpdateRequest request) {
        return webClient.post()
                .uri("/users/" + request.getUserId())
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(response.statusCode(), error))))
                .bodyToMono(CoreServiceUserResponse.class);
    }
}
