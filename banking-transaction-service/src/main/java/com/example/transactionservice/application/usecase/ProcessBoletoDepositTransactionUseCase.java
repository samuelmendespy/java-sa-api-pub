package com.example.transactionservice.application.usecase;

import com.example.transactionservice.application.command.ProcessBoletoDepositCommand;
import com.example.transactionservice.application.result.TransactionProcessingResult;

public interface ProcessBoletoDepositTransactionUseCase {
    TransactionProcessingResult execute(ProcessBoletoDepositCommand command);
}
