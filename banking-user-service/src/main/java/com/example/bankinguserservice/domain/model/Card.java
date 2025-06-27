package com.example.bankinguserservice.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "tb_card")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "number"})
@ToString
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String number;

    @Column(precision = 13, scale = 2, nullable = false)
    private BigDecimal creditLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}