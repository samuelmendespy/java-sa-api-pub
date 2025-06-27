package com.example.bankinguserservice.usecase.user;


import com.example.bankinguserservice.domain.model.User;
import com.example.bankinguserservice.domain.service.UserService;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class GetUserByIdUseCase {

    private final UserService userService;

    public GetUserByIdUseCase(UserService userService) {
        this.userService = userService;
    }

    public User execute(String userId) {
        User existingUser = userService.getUserById(userId);
        if (existingUser == null) {
            throw new NotFoundException("User not found with ID: " + userId);
        }
        return existingUser;
    }
}