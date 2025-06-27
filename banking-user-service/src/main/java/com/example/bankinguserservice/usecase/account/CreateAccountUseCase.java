package com.example.bankinguserservice.usecase.account;

import com.example.bankinguserservice.domain.model.Account;
import com.example.bankinguserservice.domain.model.User;
import com.example.bankinguserservice.domain.service.AccountService;
import com.example.bankinguserservice.domain.service.UserService;
import com.example.bankinguserservice.infrastructure.dto.AccountCreationRequestDTO;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class CreateAccountUseCase {

    private final UserService userService;
    private final AccountService accountService;

    public CreateAccountUseCase(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Transactional
    public Account execute(
            String userId,
            AccountCreationRequestDTO request
    ) {
        User checkedUser = userService.getUserById(userId);

        if (checkedUser == null) {
            throw new NotFoundException("User not found");
        }

        if (checkedUser.getAccount() != null) {
            throw new IllegalStateException("User already registered an account! ");
        }

        Account newAccount = new Account();
        newAccount.setAgency(request.getAgency());
        newAccount.setNumber(request.getNumber());
        newAccount.setBalance(BigDecimal.ZERO);
        newAccount.setOverdraftLimit(BigDecimal.ZERO);
        newAccount.setCards(new ArrayList<>());

        Account savedAccount = accountService.createAccount(newAccount);

        // Assign the saved account to User
        checkedUser.setAccount(savedAccount);

        // Update User
        userService.updateUser(checkedUser);
        return savedAccount;
    }
}