package com.example.transactionservice.domain.port.out;

import com.example.transactionservice.application.dto.BankAuthorizationRequest;
import com.example.transactionservice.application.dto.BankAuthorizationResponse;

public interface BankGateway {
    BankAuthorizationResponse authorizeAndDebit(BankAuthorizationRequest request);
}
