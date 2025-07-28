package com.example.account_manager.application.service

import com.example.account_manager.domain.model.Customer
import com.example.account_manager.domain.model.port.CustomerRepository
import com.example.account_manager.infrastructure.controller.PurchaseController
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CustomerService (
    private val customerRepository: CustomerRepository
){
    private val log = LoggerFactory.getLogger(PurchaseController::class.java)

    fun createCustomer(customer: Customer): Customer {
        return customerRepository.save(customer)
    }

    fun getByCpf(cpf: String) : Customer {
        log.info("CustomerService: Foi recebido o pedido para obter o cliente de id {}", cpf )
        return customerRepository.findByCpf(cpf)
    }

    fun updateCustomer(id: String, updatedCustomer : Customer) : Customer? {
        log.info("CustomerService: Foi recebido o pedido para atualizar o cliente de id {}", updatedCustomer.cpf )
        val existingCustomer = customerRepository.findById(id)

        return existingCustomer.let {
            val customerToSave = Customer(
                cpf = existingCustomer.get().cpf,
                name = updatedCustomer.name,
                email = updatedCustomer.email,
                mobilePhoneNumber = updatedCustomer.mobilePhoneNumber
            )

            customerRepository.save(customerToSave)
        }
    }

    fun deleteByCpf(cpf: String) : Boolean {
        log.info("CustomerService: Foi recebido o pedido para obter o cliente de id {}", cpf )

        return if (customerRepository.existsByCpf(cpf)) {
            customerRepository.deleteByCpf(cpf)
            true
        } else {
            false
        }
    }
}