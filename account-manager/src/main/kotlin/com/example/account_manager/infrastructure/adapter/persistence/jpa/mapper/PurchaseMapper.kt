package com.example.account_manager.infrastructure.adapter.persistence.jpa.mapper

import com.example.account_manager.domain.model.Purchase
import com.example.account_manager.infrastructure.adapter.persistence.jpa.entity.PurchaseJpaEntity
import org.springframework.stereotype.Component

@Component
class PurchaseMapper {

    fun toDomain(jpaEntity: PurchaseJpaEntity): Purchase {
        return Purchase(
            id = jpaEntity.id,
            numberNota = jpaEntity.numberNota,
            valorCompra = jpaEntity.valorCompra,
            cnpjLoja = jpaEntity.cnpjLoja,
            machineReference = jpaEntity.machineReference,
            purchaseTime = jpaEntity.purchaseTime,
            customerCpf = jpaEntity.customerCpf
        )
    }

    fun toJpaEntity(domain: Purchase): PurchaseJpaEntity {
        return PurchaseJpaEntity(
            id = domain.id,
            numberNota = domain.numberNota,
            valorCompra = domain.valorCompra,
            cnpjLoja = domain.cnpjLoja,
            machineReference = domain.machineReference,
            purchaseTime = domain.purchaseTime,
            customerCpf = domain.customerCpf
        )
    }
}