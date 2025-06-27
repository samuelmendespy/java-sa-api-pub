package com.example.bankinguserservice.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class AccountDeletionRequestDTO {
    private String accountId;
    private Optional<String> userId = Optional.empty();
}