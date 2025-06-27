package com.example.coreservice.application.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdateRequest {
    private String accountId;
    private String name;
    private String accountNumber;
    private String accountAgency;
}