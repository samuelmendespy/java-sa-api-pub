package com.example.bankinguserservice.usecase.user;

import com.example.bankinguserservice.domain.model.User;
import com.example.bankinguserservice.domain.service.UserService;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteUserUseCase {
    private final UserService userService;

    public DeleteUserUseCase(
            UserService userService
    ) {
        this.userService = userService;
    }

    @Transactional
    public void execute(
            String id
    ) {
        User existingUser = userService.getUserById(id);

        if (existingUser == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }

        userService.deleteUser(id);
        return;
    }
}