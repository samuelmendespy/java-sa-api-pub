package com.example.account_manager.infrastructure.controller

import com.example.account_manager.application.service.AccountService
import com.example.account_manager.application.service.PurchaseService
import com.example.account_manager.domain.model.Account
import com.example.account_manager.domain.model.Purchase
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/purchases")
class PurchaseController (
    private val purchaseService: PurchaseService,
    private val accountService: AccountService
) {

    @GetMapping("/history/{id}")
    fun getPurchaseHistoryByAccountId(
        @PathVariable id: String, pageable: Pageable
    ): ResponseEntity<Page<Purchase>> {
        return accountService.getAccountById(id)
            ?.let { account ->
                val cpf = account.cpf
                purchaseService.getPurchaseHistoryByCustomerCpf(cpf, pageable)
                    ?.let { ResponseEntity.ok(it) }
                    ?: ResponseEntity.notFound().build()
        } ?: run {
            ResponseEntity.notFound().build()
        }
    }
}