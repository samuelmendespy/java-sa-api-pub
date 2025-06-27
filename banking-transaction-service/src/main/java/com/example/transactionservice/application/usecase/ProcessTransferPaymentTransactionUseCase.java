package com.example.transactionservice.application.usecase;

import com.example.transactionservice.application.command.ProcessTransferPaymentCommand;
import com.example.transactionservice.application.result.TransactionProcessingResult;

public interface ProcessTransferPaymentTransactionUseCase {
    TransactionProcessingResult execute(ProcessTransferPaymentCommand command);
}


