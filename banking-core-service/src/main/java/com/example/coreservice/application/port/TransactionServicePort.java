package com.example.coreservice.application.port;

import com.example.coreservice.application.dto.transaction.CoreServiceTransactionResponse;
import com.example.coreservice.application.dto.transaction.TransactionRecordRequest;
import reactor.core.publisher.Mono;

public interface TransactionServicePort {
    Mono<CoreServiceTransactionResponse> recordTransaction(TransactionRecordRequest request);
}
