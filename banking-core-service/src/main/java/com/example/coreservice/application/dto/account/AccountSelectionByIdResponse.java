package com.example.coreservice.application.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSelectionByIdResponse {
    private String id;
    private String number;
    private String agency;
    private String name;
}