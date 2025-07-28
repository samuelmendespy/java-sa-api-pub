package com.example.account_manager.infrastructure.adapter.persistence.jpa.repository

import com.example.account_manager.infrastructure.adapter.persistence.jpa.entity.CustomerJpaEntity
import org.springframework.stereotype.Repository

@Repository
interface CustomerJpaSpringRepository {
    fun findByCpf(cpf : String) : CustomerJpaEntity
    fun deleteByCpf(cpf : String) : Boolean
    fun save(customer: CustomerJpaEntity) : CustomerJpaEntity
    fun existsByCpf(cpf: String) : Boolean
    fun findById(id: String) : CustomerJpaEntity
}