package com.example.transactionservice.application.usecase;

import com.example.transactionservice.application.command.ProcessBoletoPaymentCommand;
import com.example.transactionservice.application.result.TransactionProcessingResult;

public interface ProcessBoletoPaymentTransactionUseCase {
    TransactionProcessingResult execute(ProcessBoletoPaymentCommand command);
}
