package com.example.coreservice.application.port;

import com.example.coreservice.application.dto.user.CoreServiceUserResponse;
import com.example.coreservice.application.dto.user.UserCreationRequest;
import com.example.coreservice.application.dto.user.UserSelectionByIdRequest;
import com.example.coreservice.application.dto.user.UserUpdateRequest;
import reactor.core.publisher.Mono;

public interface UserServicePort {
    Mono<CoreServiceUserResponse> getUser(UserSelectionByIdRequest accountId);
    Mono<CoreServiceUserResponse> updateUser(UserUpdateRequest request);
    Mono<CoreServiceUserResponse> createUser(UserCreationRequest request);
}