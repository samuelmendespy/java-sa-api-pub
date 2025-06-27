package com.example.coreservice.application.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdateResponse {
    private String userId;
    private String name;
    private String accountNumber;
    private String accountAgency;
}