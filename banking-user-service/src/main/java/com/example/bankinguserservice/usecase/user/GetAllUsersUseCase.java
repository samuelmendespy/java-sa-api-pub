package com.example.bankinguserservice.usecase.user;

import com.example.bankinguserservice.domain.model.User;
import com.example.bankinguserservice.domain.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetAllUsersUseCase {
    private final UserService userService;

    public GetAllUsersUseCase(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public Page<User> execute(Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);
        return users;
    }
}