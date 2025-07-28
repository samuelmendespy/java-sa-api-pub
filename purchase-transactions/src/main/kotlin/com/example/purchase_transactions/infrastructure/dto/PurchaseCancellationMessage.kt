package com.example.purchase_transactions.infrastructure.dto

import com.example.purchase_transactions.domain.model.Purchase
import kotlinx.datetime.LocalDateTime

class PurchaseCancellationMessage (
    var id : String,
    val purchase: Purchase,
    val description: String,
    val cancelRequestTime: LocalDateTime,
    var processTime : LocalDateTime
)