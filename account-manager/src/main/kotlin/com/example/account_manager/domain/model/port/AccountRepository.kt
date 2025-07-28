package com.example.account_manager.domain.model.port

import com.example.account_manager.domain.model.Account

interface AccountRepository {
    fun findByCpf(cpf : String) : Account
    fun deleteByCpf(cpf: String) : Boolean
    fun save(account : Account) : Account
    fun existsByCpf(cpf: String) : Boolean
}