package com.example.bankinguserservice.infrastructure.dto;

import com.example.bankinguserservice.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String cpf;
    private String name;
    private AccountDTO account;

    public static UserDTO fromDomain(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setCpf(user.getCpf());
        dto.setName(user.getName());
        if (user.getAccount() != null) {
            dto.setAccount(AccountDTO.fromDomain(user.getAccount()));
        }
        return dto;
    }

    public User toDomain() {
        User user = new User();
        user.setId(this.id);
        user.setCpf(this.cpf);
        user.setName(this.name);
        if (this.account != null) {
            user.setAccount(this.account.toDomain());
        }
        return user;
    }
}