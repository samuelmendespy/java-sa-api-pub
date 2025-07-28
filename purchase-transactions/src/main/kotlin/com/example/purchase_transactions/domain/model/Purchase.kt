package com.example.purchase_transactions.domain.model

import  kotlinx.datetime.LocalDateTime

class Purchase (
    var id: String,
    val numberNota: String,
    val valorCompra: String,
    val cnpjLoja: String,
    val machineReference: String,
    val purchaseTime: LocalDateTime,
    val custommerCpf: String
)