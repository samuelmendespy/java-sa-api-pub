package com.example.account_manager.infrastructure.adapter.persistence.jpa.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kotlinx.datetime.LocalDateTime

@Entity
@Table(name = "purchases")
data class PurchaseJpaEntity (
    @Id
    var id: String,
    val numberNota: String,
    val valorCompra: String,
    val cnpjLoja: String,
    val machineReference: String,
    val purchaseTime: LocalDateTime,
    val customerCpf: String
)