package com.example.bankinguserservice.usecase.card;

import com.example.bankinguserservice.domain.model.Account;
import com.example.bankinguserservice.domain.model.Card;
import com.example.bankinguserservice.domain.service.AccountService;
import com.example.bankinguserservice.domain.service.CardService;
import com.example.bankinguserservice.infrastructure.dto.CardCreationRequestDTO;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class CreateCardUseCase {

    private final CardService cardService;
    private final AccountService accountService;

    public CreateCardUseCase(AccountService accountService, CardService cardService) {
        this.accountService = accountService;
        this.cardService = cardService;
    }

    @Transactional
    public Card execute(
            CardCreationRequestDTO request
    ) {
        Account existingAccount = accountService.getAccountById(request.getAccountId());

        if (existingAccount == null ) {
            throw new NotFoundException("Account not found with ID!");
        }

        BigDecimal accountLimit = existingAccount.getOverdraftLimit();
        BigDecimal percentage = new BigDecimal("0.3");
        BigDecimal defaultCardLimit = accountLimit.multiply(percentage);
        String defaultCardNumber = "1234 1234 0000 1234";

        // Create Card
        Card card = new Card();
        card.setNumber(defaultCardNumber);
        card.setCreditLimit(defaultCardLimit);
        card.setAccount(existingAccount);

        // Save card
        Card savedCard = cardService.createCard(card);

        // Assign card to account
        existingAccount.addCard(savedCard);
        accountService.updateAccount(existingAccount);

        // Return saved card
        return savedCard;
    }
}