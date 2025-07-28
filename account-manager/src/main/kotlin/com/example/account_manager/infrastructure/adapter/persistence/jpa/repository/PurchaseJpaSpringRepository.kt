package com.example.account_manager.infrastructure.adapter.persistence.jpa.repository

import com.example.account_manager.infrastructure.adapter.persistence.jpa.entity.PurchaseJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PurchaseJpaSpringRepository : JpaRepository<PurchaseJpaEntity, String> {
    fun findByCustomerCpf(customerCpf : String, pageable: Pageable) : Page<PurchaseJpaEntity>
    fun save(purchase: PurchaseJpaEntity) : PurchaseJpaEntity
}