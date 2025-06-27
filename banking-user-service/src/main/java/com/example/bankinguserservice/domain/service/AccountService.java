package com.example.bankinguserservice.domain.service;

import com.example.bankinguserservice.domain.model.Account;
import com.example.bankinguserservice.domain.repository.AccountRepository;
import com.example.bankinguserservice.infrastructure.dto.AccountSelectionResponseDTO;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public Account getAccountById(String id) {
        Account account = accountRepository.findById(id).orElse(null);
        return account;
    }

    @Transactional(readOnly = true)
    public Page<AccountSelectionResponseDTO> getAllAccounts(Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(pageable);
        return accounts.map(AccountSelectionResponseDTO::fromDomain);
    }

    @Transactional
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccount(Account account) {
        String id = account.getId();
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found with ID: " + id));

        existingAccount.setAgency(account.getAgency());
        existingAccount.setNumber(account.getNumber());

        Account updatedAccount = accountRepository.save(existingAccount);

        Hibernate.initialize(updatedAccount.getCards());

        return updatedAccount;
    }

    @Transactional
    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }
}