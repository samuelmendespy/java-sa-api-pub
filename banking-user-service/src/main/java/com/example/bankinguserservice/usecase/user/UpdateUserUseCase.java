package com.example.bankinguserservice.usecase.user;

import com.example.bankinguserservice.domain.model.User;
import com.example.bankinguserservice.domain.service.UserService;
import com.example.bankinguserservice.infrastructure.dto.UserUpdateRequestDTO;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateUserUseCase {
    private final UserService userService;

    public UpdateUserUseCase(
            UserService userService
    ) {
        this.userService = userService;
    }

    @Transactional
    public User execute(UserUpdateRequestDTO dto) {
        User existingUser = userService.getUserById(dto.getId());

        if (existingUser == null) {
            throw new NotFoundException("User not found with ID: " + dto.getId());
        }

        existingUser.setId(dto.getId());
        existingUser.setName(dto.getName());
        existingUser.setCpf(dto.getCpf());
        existingUser.setAccount(dto.getAccount());
        return userService.updateUser(existingUser);
    }
}