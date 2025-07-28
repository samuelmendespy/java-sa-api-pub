package com.example.account_manager.infrastructure.adapter.persistence.jpa.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "customers")
data class CustomerJpaEntity (
    @Id
    val cpf : String,
    val name : String,
    val mobilePhoneNumber : String,
    val email : String
)