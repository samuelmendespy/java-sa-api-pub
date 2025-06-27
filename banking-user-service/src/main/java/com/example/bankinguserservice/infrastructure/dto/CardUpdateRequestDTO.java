package com.example.bankinguserservice.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CardUpdateRequestDTO {
    @NotBlank(message = "Account id cannot be empty")
    private String accountId;
    @NotBlank(message = "Card limit cannot be empty")
    private BigDecimal creditLimit;
}