package com.example.bankinguserservice.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreationRequestDTO {
    private String number;
    private String agency;
}