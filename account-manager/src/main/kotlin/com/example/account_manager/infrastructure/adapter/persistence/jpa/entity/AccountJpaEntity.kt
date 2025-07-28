package com.example.account_manager.infrastructure.adapter.persistence.jpa.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "accounts")
data class AccountJpaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id : String,
    val cpf : String,
    val points : Int,
)