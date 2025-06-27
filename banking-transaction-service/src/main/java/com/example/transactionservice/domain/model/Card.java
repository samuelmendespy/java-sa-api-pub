package com.example.transactionservice.domain.model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Card {

    private String id; // ID interno do cartão no sistema de autorização (se aplicável)
    private String tokenizedCardNumber; // Número do cartão tokenizado (segurança!)
    private String accountId; // Referência à conta bancária associada

    // Construtor
    public Card(String id, String tokenizedCardNumber, String accountId) {
        this.id = id;
        this.tokenizedCardNumber = tokenizedCardNumber;
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", tokenizedCardNumber='" + (tokenizedCardNumber != null ? "****" + tokenizedCardNumber.substring(tokenizedCardNumber.length() - 4) : "N/A") + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}