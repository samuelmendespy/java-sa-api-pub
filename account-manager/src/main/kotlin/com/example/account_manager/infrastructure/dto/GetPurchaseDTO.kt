package com.example.account_manager.infrastructure.dto

import com.example.account_manager.domain.model.Purchase
import kotlinx.datetime.LocalDateTime

class GetPurchaseDTO (
    var id : String,
    val purchase: Purchase,
    val description: String,
    val cancelRequestTime: LocalDateTime,
    var processTime : LocalDateTime
)