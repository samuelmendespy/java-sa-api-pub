package com.example.bankinguserservice.infrastructure.dto;

import com.example.bankinguserservice.domain.model.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private String id;
    private String number;
    private BigDecimal creditLimit;

    public static CardDTO fromDomain(Card card) {
        CardDTO dto = new CardDTO();
        dto.setId(card.getId());
        dto.setNumber(card.getNumber());
        dto.setCreditLimit(card.getCreditLimit());
        return dto;
    }

    public Card toDomain() {
        Card card = new Card();
        card.setId(this.id);
        card.setNumber(this.number);
        card.setCreditLimit(this.creditLimit);
        return card;
    }
}