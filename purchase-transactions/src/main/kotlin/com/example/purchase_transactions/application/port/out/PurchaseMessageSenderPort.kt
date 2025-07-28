package com.example.purchase_transactions.application.port.out

import com.example.purchase_transactions.infrastructure.dto.PurchaseCancellationMessage
import com.example.purchase_transactions.infrastructure.dto.ValidPurchaseMessage

interface PurchaseMessageSenderPort {
    fun sendValidPurchaseMessage(message: ValidPurchaseMessage)
    fun sendCancellationMessage(message: PurchaseCancellationMessage)
}