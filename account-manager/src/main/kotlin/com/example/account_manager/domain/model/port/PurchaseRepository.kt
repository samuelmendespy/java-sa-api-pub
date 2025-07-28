package com.example.account_manager.domain.model.port

import com.example.account_manager.domain.model.Purchase
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Page

interface PurchaseRepository {
    fun findByCustommerCpf(customerCpf : String, pageable: Pageable) : Page<Purchase>
    fun save(purchase: Purchase) : Purchase
}