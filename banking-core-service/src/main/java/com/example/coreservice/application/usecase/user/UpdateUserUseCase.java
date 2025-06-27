package com.example.coreservice.application.usecase.user;

import com.example.coreservice.application.dto.user.CoreServiceUserResponse;
import com.example.coreservice.application.dto.user.UserUpdateRequest;
import com.example.coreservice.application.port.UserServicePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateUserUseCase {

    private final UserServicePort userServicePort;

    public UpdateUserUseCase(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    public Mono<CoreServiceUserResponse> execute(UserUpdateRequest request) {
        return userServicePort.updateUser(request);
    }
}