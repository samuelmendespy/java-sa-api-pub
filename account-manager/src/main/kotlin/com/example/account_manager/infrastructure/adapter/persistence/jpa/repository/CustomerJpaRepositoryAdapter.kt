package com.example.account_manager.infrastructure.adapter.persistence.jpa.repository

import com.example.account_manager.domain.model.Customer
import com.example.account_manager.domain.model.port.CustomerRepository
import com.example.account_manager.infrastructure.adapter.persistence.jpa.mapper.CustomerMapper
import org.springframework.stereotype.Repository

@Repository
class CustomerJpaRepositoryAdapter(
    private val jpaSpringRepository: CustomerJpaSpringRepository,
    private val customerMapper: CustomerMapper
) : CustomerRepository {


    override fun findByCpf(customerCpf: String): Customer {
        return jpaSpringRepository.findByCpf(customerCpf).let {
            customerMapper.toDomain(it)
        }
    }

    override fun deleteByCpf(cpf: String): Boolean {
       return jpaSpringRepository.deleteByCpf(cpf)
    }

    override fun save(customer: Customer): Customer {
        val jpaEntity = customerMapper.toJpaEntity(customer)
        val savedJpaEntity = jpaSpringRepository.save(jpaEntity)
        return customerMapper.toDomain(savedJpaEntity)
    }

    override fun existsByCpf(cpf: String): Boolean {
        return jpaSpringRepository.existsByCpf(cpf)
    }

}