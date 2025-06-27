package com.example.bankinguserservice.domain.service;

import com.example.bankinguserservice.domain.model.Card;
import com.example.bankinguserservice.domain.repository.CardRepository;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional(readOnly = true)
    public Card getCardById(String id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Card not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Card> getCardsByAccountId(String accountId) {
        return cardRepository.findByAccountId(accountId);
    }

    @Transactional(readOnly = true)
    public Page<Card> getAllCards(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    @Transactional
    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    @Transactional
    public Card updateCard(Card card) {
        if (!cardRepository.existsById(card.getId())) {
            throw new NotFoundException("Card not found with ID: " + card.getId());
        }
        return cardRepository.save(card);
    }

    @Transactional
    public void deleteCard(String id) {
        if (!cardRepository.existsById(id)) {
            throw new NotFoundException("Card not found with ID: " + id);
        }
        cardRepository.deleteById(id);
    }
}