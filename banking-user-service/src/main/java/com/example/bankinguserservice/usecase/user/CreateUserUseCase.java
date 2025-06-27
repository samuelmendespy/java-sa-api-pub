package com.example.bankinguserservice.usecase.user;

import com.example.bankinguserservice.domain.model.User;
import com.example.bankinguserservice.domain.repository.UserRepository;
import com.example.bankinguserservice.infrastructure.dto.UserCreationRequestDTO;
import com.example.bankinguserservice.infrastructure.exception.UserAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final GetUserByCpfUseCase getUserByCpfUseCase;

    public CreateUserUseCase(UserRepository userRepository, GetUserByCpfUseCase getUserByCpfUseCase) {
        this.userRepository = userRepository;
        this.getUserByCpfUseCase = getUserByCpfUseCase;
    }

    @Transactional
    public User execute(
            UserCreationRequestDTO dto
    ) {
        User existingUser = getUserByCpfUseCase.execute(dto.getCpf());
        if (existingUser != null) {
            throw new UserAlreadyExistsException("User already registered! ");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setCpf(dto.getCpf());
        user.setAccount(null);

        return userRepository.save(user);
    }
}