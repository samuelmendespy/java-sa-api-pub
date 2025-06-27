package com.example.bankinguserservice.domain.repository;

import com.example.bankinguserservice.domain.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
    List<Card> findByAccountId(String accountId);
}