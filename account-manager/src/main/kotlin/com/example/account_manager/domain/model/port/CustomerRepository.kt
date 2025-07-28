package com.example.account_manager.domain.model.port

import com.example.account_manager.domain.model.Customer

interface CustomerRepository {
    fun findByCpf(cpf : String) : Customer
    fun deleteByCpf(cpf: String) : Boolean
    fun save(customer : Customer) : Customer
    fun existsByCpf(cpf: String) : Boolean
}