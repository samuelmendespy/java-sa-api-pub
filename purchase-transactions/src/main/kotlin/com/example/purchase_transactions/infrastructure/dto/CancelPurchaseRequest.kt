package com.example.purchase_transactions.infrastructure.dto

import com.example.purchase_transactions.domain.model.Purchase
import kotlinx.datetime.LocalDateTime

class CancelPurchaseRequest (
    val token : Int,
    val purchase: Purchase,
    val cancelRequestTime: LocalDateTime,
    val description: String
)