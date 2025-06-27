package com.example.bankinguserservice.usecase.account;

import com.example.bankinguserservice.domain.model.Account;
import com.example.bankinguserservice.domain.service.AccountService;
import com.example.bankinguserservice.infrastructure.dto.AccountSelectionResponseDTO;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class GetAccountByIdUseCase {
    private final AccountService accountService;

    public GetAccountByIdUseCase(AccountService accountService) {
        this.accountService = accountService;
    }

    public AccountSelectionResponseDTO execute(String id) {
        Account existingAccount = accountService.getAccountById(id);
        if (existingAccount == null) {
            throw new NotFoundException("Account was not found! ");
        }

        return AccountSelectionResponseDTO.fromDomain(existingAccount);
    }
}