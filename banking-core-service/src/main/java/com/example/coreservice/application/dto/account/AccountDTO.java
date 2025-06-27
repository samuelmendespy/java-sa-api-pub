package com.example.coreservice.application.dto.account;

import com.example.coreservice.application.dto.card.CardDTO;
import com.example.coreservice.domain.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private String id;
    private String number;
    private String agency;
    private String name;
    private BigDecimal balance;
    private BigDecimal overdraftLimit;
    private List<CardDTO> cards;

    public static AccountDTO fromDomain(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setNumber(account.getNumber());
        dto.setAgency(account.getAgency());
        dto.setBalance(account.getBalance());
        dto.setOverdraftLimit(account.getOverdraftLimit());
        if (account.getCards() != null) {
            dto.setCards(account.getCards().stream()
                    .map(CardDTO::fromDomain)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public Account toDomain() {
        Account account = new Account();
        account.setId(this.id);
        account.setNumber(this.number);
        account.setAgency(this.agency);
        account.setBalance(this.balance);
        account.setOverdraftLimit(this.overdraftLimit);
        if (this.cards != null) {
            account.setCards(this.cards.stream()
                    .map(CardDTO::toDomain)
                    .collect(Collectors.toList()));
        }
        return account;
    }
}