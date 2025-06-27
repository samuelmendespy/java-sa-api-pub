package com.example.bankinguserservice.infrastructure.dto;

import com.example.bankinguserservice.domain.model.Account;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateRequestDTO {
    @NotBlank(message = "ID cannot be empty")
    private String id;

    @NotBlank(message = "CPF cannot be empty")
    private String cpf;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private Account account;
}