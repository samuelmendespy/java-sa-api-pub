package com.example.bankinguserservice.usecase.card;

import com.example.bankinguserservice.domain.model.Account;
import com.example.bankinguserservice.domain.model.Card;
import com.example.bankinguserservice.domain.service.AccountService;
import com.example.bankinguserservice.domain.service.CardService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteCardUseCase {
    private final CardService cardService;
    private final AccountService accountService;

    public DeleteCardUseCase(
            AccountService accountService,
            CardService cardService
    ) {
        this.accountService = accountService;
        this.cardService = cardService;
    }

    @Transactional
    public void execute(
            String cardId,
            String accountId
    ) {
        Account existingAccount = accountService.getAccountById(accountId);
        if (existingAccount == null) {
            throw new IllegalStateException("Account does not exist! ");
        }

        // Check if card exists
        Card cardToRemove = null;

        for (Card card : existingAccount.getCards()) {
            if (card.getId().equals(cardId)) {
                cardToRemove = card;
                break;
            }
        }

        if (cardToRemove == null) {
            throw new IllegalStateException("Card does not exist! ");
        }

        existingAccount.removeCard(cardToRemove);
        accountService.updateAccount(existingAccount);
        cardService.deleteCard(cardId);
        return;
    }
}