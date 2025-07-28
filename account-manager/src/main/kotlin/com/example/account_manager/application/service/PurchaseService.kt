package com.example.account_manager.application.service

import com.example.account_manager.domain.model.Purchase
import com.example.account_manager.domain.model.port.PurchaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PurchaseService(private val purchaseRepository: PurchaseRepository) {

    fun getPurchaseHistoryByCustomerCpf(customerCpf: String, pageable: Pageable): Page<Purchase> {
        return purchaseRepository.findByCustommerCpf(customerCpf, pageable)
    }

    fun savePurchase(purchase: Purchase): Purchase {
        return purchaseRepository.save(purchase)
    }
}