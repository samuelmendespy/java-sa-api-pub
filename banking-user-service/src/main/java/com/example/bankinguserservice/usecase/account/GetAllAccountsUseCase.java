package com.example.bankinguserservice.usecase.account;

import com.example.bankinguserservice.domain.service.AccountService;
import com.example.bankinguserservice.infrastructure.dto.AccountSelectionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetAllAccountsUseCase {
    private final AccountService accountService;

    public GetAllAccountsUseCase(AccountService accountService) {
        this.accountService = accountService;
    }

    @Transactional
    public Page<AccountSelectionResponseDTO> execute(Pageable pageable) {
        return accountService.getAllAccounts(pageable);
    }
}