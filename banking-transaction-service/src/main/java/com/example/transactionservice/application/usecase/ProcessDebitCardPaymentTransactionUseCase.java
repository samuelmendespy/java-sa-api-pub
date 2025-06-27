package com.example.transactionservice.application.usecase;

import com.example.transactionservice.application.command.ProcessDebitCardPaymentCommand;
import com.example.transactionservice.application.result.TransactionProcessingResult;

public interface ProcessDebitCardPaymentTransactionUseCase {
    TransactionProcessingResult execute(ProcessDebitCardPaymentCommand command);
}