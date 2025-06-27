package com.example.bankinguserservice.usecase.card;

import com.example.bankinguserservice.domain.model.Card;
import com.example.bankinguserservice.domain.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetAllCardsUseCase {
    private final CardService cardService;

    public GetAllCardsUseCase(CardService cardService) {
        this.cardService = cardService;
    }

    @Transactional
    public Page<Card> execute(Pageable pageable) {
        Page<Card> cards = cardService.getAllCards(pageable);
        return cards;
    }
}