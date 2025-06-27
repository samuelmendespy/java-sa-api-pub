package com.example.bankinguserservice.infrastructure.dto;

import lombok.Getter;

@Getter
public class AccountUpdateResponseDTO {
    private String id;
    private String number;
    private String agency;
}