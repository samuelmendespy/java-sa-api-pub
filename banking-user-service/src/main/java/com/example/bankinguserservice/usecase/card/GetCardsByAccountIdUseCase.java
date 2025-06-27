package com.example.bankinguserservice.usecase.card;

import com.example.bankinguserservice.domain.model.Account;
import com.example.bankinguserservice.domain.model.Card;
import com.example.bankinguserservice.domain.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetCardsByAccountIdUseCase {
    private final AccountService accountService;

    public GetCardsByAccountIdUseCase(AccountService accountService ) {
        this.accountService = accountService;
    }

    @Transactional
    public List<Card> execute(
            String accountId
    ) {
        Account existingAccount = accountService.getAccountById(accountId);
        if (existingAccount == null) {
            throw new IllegalStateException("Account does not exist! ");
        }

        return existingAccount.getCards();
    }
}