package com.example.bankinguserservice.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_account")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "number"})
@ToString(exclude = {"cards"})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String number;

    @Column(nullable = false)
    private String agency;

    @Column(precision = 13, scale = 2)
    private BigDecimal balance;

    @Column(precision = 13, scale = 2)
    private BigDecimal overdraftLimit;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        if (this.cards == null) {
            this.cards = new ArrayList<>();
        }
        this.cards.add(card);
        card.setAccount(this);
    }

    public void removeCard(Card card) {
        if (this.cards != null) {
            this.cards.remove(card);
            card.setAccount(null);
        }
    }
}
