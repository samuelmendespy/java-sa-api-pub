package com.example.bankinguserservice.usecase.account;

import com.example.bankinguserservice.domain.model.Account;
import com.example.bankinguserservice.domain.service.AccountService;
import com.example.bankinguserservice.infrastructure.dto.AccountUpdateRequestDTO;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateAccountUseCase {
    private final AccountService accountService;

    public UpdateAccountUseCase(AccountService accountService) {
        this.accountService = accountService;
    }

    @Transactional
    public Account execute(
            String id,
            AccountUpdateRequestDTO request
    ) {
        Account existingAccount = accountService.getAccountById(id);
        if (existingAccount == null) {
            throw new NotFoundException("User does not exist! ");
        }

        existingAccount.setId(request.getId());
        existingAccount.setNumber(request.getNumber());
        existingAccount.setAgency(request.getAgency());

        return accountService.updateAccount(existingAccount);
    }
}