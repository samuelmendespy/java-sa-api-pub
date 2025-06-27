package com.example.bankinguserservice.usecase.account;

import com.example.bankinguserservice.domain.model.Account;
import com.example.bankinguserservice.domain.model.User;
import com.example.bankinguserservice.domain.service.AccountService;
import com.example.bankinguserservice.domain.service.UserService;
import com.example.bankinguserservice.infrastructure.dto.AccountDeletionRequestDTO;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteAccountUseCase {
    private final AccountService accountService;
    private final UserService userService;

    public DeleteAccountUseCase(
            AccountService accountService,
            UserService userService
    ) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @Transactional
    public void execute(
            AccountDeletionRequestDTO request
    ) {
        String accountId = request.getAccountId();

        Account existingAccount= accountService.getAccountById(accountId);

        if (existingAccount == null) {
            throw new NotFoundException("Account does not exist! ");
        }

        // Clear User's bank Account
        if (request.getUserId().isPresent()) {
            String userId = request.getUserId().get();
            User user = userService.getUserById(userId);
            user.setAccount(null);
            userService.updateUser(user);
        }

        accountService.deleteAccount(accountId);
        return;
    }
}