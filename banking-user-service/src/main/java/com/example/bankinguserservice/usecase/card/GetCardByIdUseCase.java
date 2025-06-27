package com.example.bankinguserservice.usecase.card;

import com.example.bankinguserservice.domain.model.Card;
import com.example.bankinguserservice.domain.service.CardService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetCardByIdUseCase {
    private final CardService cardService;

    public GetCardByIdUseCase(CardService cardService) {
        this.cardService = cardService;
    }

    @Transactional
    public Card execute(
            String cardId
    ) {
        Card existingCard = cardService.getCardById(cardId);
        if (existingCard == null) {
            throw new IllegalStateException("Card does not exist! ");
        }

        return existingCard;
    }
}