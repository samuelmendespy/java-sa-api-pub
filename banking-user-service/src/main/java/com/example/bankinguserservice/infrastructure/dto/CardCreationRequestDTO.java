package com.example.bankinguserservice.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CardCreationRequestDTO {
    @NotBlank(message = "Account id cannot be empty")
    private String accountId;
}