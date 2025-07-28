package com.example.account_manager.infrastructure.dto

import com.example.account_manager.domain.model.Purchase
import kotlinx.datetime.LocalDateTime

data class ValidPurchaseMessage (
    val purchase: Purchase,
    val processTime: LocalDateTime
)