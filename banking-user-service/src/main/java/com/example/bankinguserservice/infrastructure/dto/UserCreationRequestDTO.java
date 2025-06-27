package com.example.bankinguserservice.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCreationRequestDTO {
    @NotBlank(message = "CPF cannot be empty")
    private String cpf;

    @NotBlank(message = "Name cannot be empty")
    private String name;
}