package com.example.bankinguserservice.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdateRequestDTO {
    private String id;
    private String number;
    private String agency;
}