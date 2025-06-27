package com.example.coreservice.application.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationRequest {
    private String name;
    private String cpf;
}