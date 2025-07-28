package com.example.account_manager.infrastructure.adapter.persistence.jpa.repository

import com.example.account_manager.domain.model.Purchase
import com.example.account_manager.domain.model.port.PurchaseRepository
import com.example.account_manager.infrastructure.adapter.persistence.jpa.mapper.PurchaseMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class PurchaseJpaRepositoryAdapter(
    private val jpaSpringRepository: PurchaseJpaSpringRepository,
    private val purchaseMapper: PurchaseMapper
) : PurchaseRepository {

    override fun findByCustommerCpf(custommerCpf: String, pageable: Pageable): Page<Purchase> {
        val jpaEntitiesPage = jpaSpringRepository.findByCustomerCpf(custommerCpf, pageable)
        return jpaEntitiesPage.map { purchaseMapper.toDomain(it) }
    }

    override fun save(user: Purchase): Purchase {
        val jpaEntity = purchaseMapper.toJpaEntity(user)
        val savedJpaEntity = jpaSpringRepository.save(jpaEntity)
        return purchaseMapper.toDomain(savedJpaEntity)
    }
}