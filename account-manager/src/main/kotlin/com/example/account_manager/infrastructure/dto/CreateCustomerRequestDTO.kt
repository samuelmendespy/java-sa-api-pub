package com.example.account_manager.infrastructure.dto

class CreateCustomerRequestDTO (
    val cpf : String,
    val name : String,
    val mobilePhoneNumber: String,
    val email : String
)