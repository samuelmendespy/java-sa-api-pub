package com.example.account_manager.infrastructure.adapter.persistence.jpa.mapper

import com.example.account_manager.domain.model.Account
import com.example.account_manager.infrastructure.adapter.persistence.jpa.entity.AccountJpaEntity
import org.springframework.stereotype.Component

@Component
class AccountMapper {

    fun toDomain(jpaEntity: AccountJpaEntity): Account {
        return Account(
            id = jpaEntity.id,
            cpf = jpaEntity.cpf,
            points = jpaEntity.points
        )
    }

    fun toJpaEntity(domain: Account): AccountJpaEntity {
        return AccountJpaEntity(
            id = domain.id,
            cpf = domain.cpf,
            points = domain.points,
        )
    }
}