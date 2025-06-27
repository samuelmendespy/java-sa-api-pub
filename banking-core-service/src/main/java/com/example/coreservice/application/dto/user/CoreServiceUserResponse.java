package com.example.coreservice.application.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoreServiceUserResponse {
    private String userId;
    private String name;
    private String cpf;
    private Long accountId;
    private String accountNumber;
    private String accountAgency;
}