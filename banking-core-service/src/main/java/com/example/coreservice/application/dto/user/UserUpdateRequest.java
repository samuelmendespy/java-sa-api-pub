package com.example.coreservice.application.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String userId;
    private String name;
    private String cpf;
    private Long accountId;
    private String accountNumber;
    private String accountAgency;
}