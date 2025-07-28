package com.example.purchase_transactions.infrastructure.dto

import com.example.purchase_transactions.domain.model.Purchase

class ProcessPurchaseRequest (
    val purchase: Purchase
)