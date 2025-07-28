package com.example.account_manager.domain.model

import  kotlinx.datetime.LocalDateTime

class Purchase (
    val id: String,
    val numberNota: String,
    val valorCompra: String,
    val cnpjLoja: String,
    val machineReference: String,
    val purchaseTime: LocalDateTime,
    val customerCpf: String
)