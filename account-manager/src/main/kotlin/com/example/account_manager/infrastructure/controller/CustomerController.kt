package com.example.account_manager.infrastructure.controller

import com.example.account_manager.application.service.AccountService
import com.example.account_manager.application.service.CustomerService
import com.example.account_manager.domain.model.Account
import com.example.account_manager.domain.model.Customer
import com.example.account_manager.infrastructure.dto.CreateCustomerRequestDTO
import com.example.account_manager.infrastructure.dto.UpdateCustomerRequestDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController (
    private val accountService: AccountService,
    private val customerService: CustomerService) {
    private val log = LoggerFactory.getLogger(PurchaseController::class.java)

    @Operation(description = "Endpoint para enviar uma compra para processamento")
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createCustomer(@RequestBody customer: Customer): ResponseEntity<Customer> {
        log.info("Recebida requisição em XML de compra com nota fiscal de número {}", customer.cpf)

        return customerService.save(customer)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/{cpf}")
    fun getCustomerById(@PathVariable cpf: String): ResponseEntity<Customer> {
        return customerService.getByCpf(cpf)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{cpf}")
    fun updateCustomerById(
        @PathVariable cpf : String,
        @RequestBody request: UpdateCustomerRequestDTO): ResponseEntity<Customer> {

        val customer = customerService.getByCpf(cpf)

        val customerToUpdate = Customer(
            customer.cpf,
            customer.name,
            customer.mobilePhoneNumber,
            customer.email
        )

        return customerService.updateByCpf(customerToUpdate)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{cpf}")
    fun deleteCustomerById(@PathVariable cpf: String): ResponseEntity<Void> {
        return customerService.deleteByCpf(cpf)
            ?.let { ResponseEntity.noContent().build() }
            ?: ResponseEntity.notFound().build()
    }
}