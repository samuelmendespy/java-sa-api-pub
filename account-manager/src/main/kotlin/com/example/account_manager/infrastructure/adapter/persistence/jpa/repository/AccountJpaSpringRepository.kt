package com.example.account_manager.infrastructure.adapter.persistence.jpa.repository

import com.example.account_manager.infrastructure.adapter.persistence.jpa.entity.AccountJpaEntity
import org.springframework.stereotype.Repository

@Repository
interface AccountJpaSpringRepository {
    fun findByCpf(cpf : String) : AccountJpaEntity
    fun deleteByCpf(cpf : String) : Boolean
    fun save(customer: AccountJpaEntity) : AccountJpaEntity
    fun existsByCpf(cpf: String) : Boolean
}