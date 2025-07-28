package com.example.account_manager.infrastructure.adapter.persistence.jpa.repository

import com.example.account_manager.domain.model.Account
import com.example.account_manager.domain.model.port.AccountRepository
import com.example.account_manager.infrastructure.adapter.persistence.jpa.mapper.AccountMapper

class AccountJpaRepositoryAdapter (
    private val jpaSpringRepository: AccountJpaSpringRepository,
    private val accountMapper: AccountMapper
) : AccountRepository {

    override fun findByCpf(accountCpf: String): Account {
        return jpaSpringRepository.findByCpf(accountCpf).let {
            accountMapper.toDomain(it)
        }
    }

    override fun deleteByCpf(cpf: String): Boolean {
        return jpaSpringRepository.deleteByCpf(cpf)
    }

    override fun save(account: Account): Account {
        val jpaEntity = accountMapper.toJpaEntity(account)
        val savedJpaEntity = jpaSpringRepository.save(jpaEntity)
        return accountMapper.toDomain(savedJpaEntity)
    }

    override fun existsByCpf(cpf: String): Boolean {
        return  jpaSpringRepository.existsByCpf(cpf)
    }
}