package com.example.bankinguserservice.usecase.user;

import com.example.bankinguserservice.domain.model.User;
import com.example.bankinguserservice.domain.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class GetUserByCpfUseCase {

    private final UserService userService;

    public GetUserByCpfUseCase(UserService userService) {
        this.userService = userService;
    }

    public User execute(String cpf) {
        return userService.getUserByCpf(cpf);
    }
}