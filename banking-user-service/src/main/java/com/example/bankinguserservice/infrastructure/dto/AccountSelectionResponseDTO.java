package com.example.bankinguserservice.infrastructure.dto;

import com.example.bankinguserservice.domain.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSelectionResponseDTO {
    private String id;
    private String number;
    private String agency;

    public static AccountSelectionResponseDTO fromDomain(Account account) {
        AccountSelectionResponseDTO dto = new AccountSelectionResponseDTO();
        dto.setId(account.getId());
        dto.setNumber(account.getNumber());
        dto.setAgency(account.getAgency());
        return dto;
    }
}