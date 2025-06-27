package com.example.coreservice.application.usecase.user;

import com.example.coreservice.application.dto.user.CoreServiceUserResponse;
import com.example.coreservice.application.dto.user.UserCreationRequest;
import com.example.coreservice.application.port.UserServicePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateUserUseCase {

    private final UserServicePort userServicePort;

    public CreateUserUseCase(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    public Mono<CoreServiceUserResponse> execute(UserCreationRequest request) {
        return userServicePort.createUser(request);
    }
}