package com.example.purchase_transactions.infrastructure.dto

import com.example.purchase_transactions.domain.model.Purchase
import kotlinx.datetime.LocalDateTime

class ValidPurchaseMessage (
    val purchase: Purchase,
    val processTime: LocalDateTime
)