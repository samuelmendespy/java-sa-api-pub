package com.example.account_manager.infrastructure.controller

import com.example.account_manager.application.service.AccountService
import com.example.account_manager.domain.model.Account
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class AccountController (private val accountService: AccountService) {

    @GetMapping("/{id}")
    fun getAccountById(@PathVariable id: String): ResponseEntity<Account> {
        return accountService.getById(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    fun updateAccountById(@PathVariable id: String): ResponseEntity<Account> {
        return accountService.getById(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }
}
