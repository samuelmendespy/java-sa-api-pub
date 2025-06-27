package com.example.bankinghistoryservice.usecase;

import com.example.bankinghistoryservice.domain.enums.TransactionStatus;
import com.example.bankinghistoryservice.domain.model.Transaction;
import com.example.bankinghistoryservice.domain.service.TransactionService;
import com.example.bankinghistoryservice.infrastructure.exception.InsufficientFundsException;
import com.example.bankinghistoryservice.infrastructure.exception.InvalidTransactionException;
import com.example.bankinghistoryservice.infrastructure.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class ProcessTransactionUseCase {

    private final TransactionService transactionService;

    public ProcessTransactionUseCase(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Transactional
    public void execute(String id) {
        Transaction transaction = transactionService.findById(id);

        if (transaction == null) {
            throw new NotFoundException("Transaction not found with ID: " + id);
        }

        if (transaction.getStatus() != TransactionStatus.PENDING && transaction.getStatus() != TransactionStatus.PROCESSING) {
            System.out.println("Transaction " + id + " is not in a processable state (" + transaction.getStatus() + "). Skipping processing.");
            return;
        }

        transaction.setStatus(TransactionStatus.PROCESSING);
        transactionService.createTransaction(transaction);

        try {
            switch (transaction.getType()) {
                case DEBIT:
                    System.out.println("Simulating debit of " + transaction.getAmount() + " from account " + transaction.getSenderAccountId());
                    simulateExternalAccountOperation(transaction.getSenderAccountId(), transaction.getAmount(), "DEBIT");

                    transaction.setStatus(TransactionStatus.COMPLETED);
                    break;

                case CREDIT:
                    System.out.println("Simulating credit of " + transaction.getAmount() + " to account " + transaction.getRecipientInfo());
                    simulateExternalAccountOperation(transaction.getRecipientInfo(), transaction.getAmount(), "CREDIT");

                    transaction.setStatus(TransactionStatus.COMPLETED);
                    break;

                case TRANSFER:
                    if (transaction.getRecipientInfo() == null || transaction.getRecipientInfo().isEmpty()) {
                        throw new InvalidTransactionException("Destination account is required for TRANSFER type.");
                    }

                    System.out.println("Simulating debit of " + transaction.getAmount() + " from account " + transaction.getSenderAccountId() + " for transfer.");
                    simulateExternalAccountOperation(transaction.getSenderAccountId(), transaction.getAmount(), "DEBIT"); // This might throw InsufficientFundsException

                    System.out.println("Simulating credit of " + transaction.getAmount() + " to account " + transaction.getRecipientInfo() + " for transfer.");
                    simulateExternalAccountOperation(transaction.getRecipientInfo(), transaction.getAmount(), "CREDIT");

                    transaction.setStatus(TransactionStatus.COMPLETED);
                    break;

                default:
                    throw new InvalidTransactionException("Unsupported transaction type: " + transaction.getType());
            }
            transaction.setFailureReason(null);

        } catch (InsufficientFundsException e) {
            transaction.setStatus(TransactionStatus.FAILED);
            transaction.setFailureReason("Insufficient funds: " + e.getMessage());
            System.err.println("Transaction " + id + " failed due to insufficient funds: " + e.getMessage());
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
            transaction.setFailureReason("Processing failed: " + e.getMessage());
            System.err.println("Transaction " + id + " failed during processing: " + e.getMessage());
        } finally {
            transactionService.createTransaction(transaction);
        }
    }

        /**
         * Placeholder for simulating an external account service operation.
         * In a real application, this would be an actual HTTP call to the banking-accounts-service.
         *
         * @param accountId The ID of the account to operate on.
         * @param amount The amount to debit/credit.
         * @param operationType "DEBIT" or "CREDIT"
         * @throws InsufficientFundsException if simulating a debit and criteria met.
         */
        private void simulateExternalAccountOperation(String accountId, BigDecimal amount, String operationType) {
            // This is a mock. In a real scenario, this would be an actual REST call.
            // For demonstration, let's simulate a failure for a specific account or amount.
            if ("mockFailAccount".equals(accountId) && "DEBIT".equals(operationType)) {
                throw new InsufficientFundsException("Simulated insufficient funds for account " + accountId);
            }
            System.out.println("Simulated successful " + operationType + " operation on account " + accountId + " for amount " + amount);
            // Add a small delay to simulate network latency
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
}

