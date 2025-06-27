package com.example.bankinguserservice.usecase.card;

import com.example.bankinguserservice.domain.model.Account;
import com.example.bankinguserservice.domain.model.Card;
import com.example.bankinguserservice.domain.service.AccountService;
import com.example.bankinguserservice.domain.service.CardService;
import com.example.bankinguserservice.infrastructure.dto.CardUpdateRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class UpdateCardUseCase {
    private final CardService cardService;
    private final AccountService accountService;

    public UpdateCardUseCase(AccountService accountService, CardService cardService) {
        this.accountService = accountService;
        this.cardService = cardService;
    }

    @Transactional
    public Card execute(
            String cardId,
            CardUpdateRequestDTO request
    ) {
        Card existingCard = cardService.getCardById(cardId);

        if (existingCard == null) {
            throw new IllegalStateException("Card does not exist! ");
        }

        // TODO: Get account by card id
        Account existingAccount = accountService.getAccountById(request.getAccountId());

        // Update card limit
        BigDecimal newCardLimit = new BigDecimal("0.0");
        BigDecimal currentCardLimit = existingCard.getCreditLimit();
        BigDecimal maxCardLimit = existingAccount.getOverdraftLimit().multiply(new BigDecimal("0.5"));

        if (currentCardLimit.compareTo(maxCardLimit) > 0) {
            newCardLimit = maxCardLimit;
        } else if (currentCardLimit.compareTo(newCardLimit) > 0 && currentCardLimit.compareTo(maxCardLimit) < 0) {
            newCardLimit = request.getCreditLimit();
        }

        existingCard.setCreditLimit(newCardLimit);

        // TODO: Add a card number generator
        String defaultCardNumber = "1234 1234 0000 1234";
        existingCard.setNumber(defaultCardNumber);

        Card updatedCard = cardService.updateCard(existingCard);
        return updatedCard;
    }
}