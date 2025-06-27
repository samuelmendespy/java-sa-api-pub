package com.example.coreservice.application.usecase.user;

import com.example.coreservice.application.dto.user.CoreServiceUserResponse;
import com.example.coreservice.application.dto.user.UserSelectionByIdRequest;
import com.example.coreservice.application.port.UserServicePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetUserByIdUseCase {

    private final UserServicePort userServicePort;

    public GetUserByIdUseCase(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    public Mono<CoreServiceUserResponse> execute(UserSelectionByIdRequest request) {
        return userServicePort.getUser(request);
    }
}