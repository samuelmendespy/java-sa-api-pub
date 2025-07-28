package com.example.account_manager.infrastructure.adapter.persistence.jpa.mapper

import com.example.account_manager.domain.model.Customer
import com.example.account_manager.infrastructure.adapter.persistence.jpa.entity.CustomerJpaEntity
import org.springframework.stereotype.Component

@Component
class CustomerMapper {

    fun toDomain(jpaEntity: CustomerJpaEntity): Customer {
        return Customer(
            cpf = jpaEntity.cpf,
            name = jpaEntity.name,
            mobilePhoneNumber = jpaEntity.mobilePhoneNumber,
            email = jpaEntity.email,
        )
    }

    fun toJpaEntity(domain: Customer): CustomerJpaEntity {
        return CustomerJpaEntity(
            cpf = domain.cpf,
            name = domain.name,
            mobilePhoneNumber = domain.mobilePhoneNumber,
            email = domain.email
        )
    }
}