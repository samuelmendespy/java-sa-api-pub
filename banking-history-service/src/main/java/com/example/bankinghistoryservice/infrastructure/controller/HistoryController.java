package com.example.bankinghistoryservice.infrastructure.controller;

import com.example.bankinghistoryservice.domain.model.Transaction;
import com.example.bankinghistoryservice.infrastructure.dto.TransactionDTO;
import com.example.bankinghistoryservice.infrastructure.dto.UpdateTransactionStatusRequest;
import com.example.bankinghistoryservice.usecase.CreateTransactionUseCase;
import com.example.bankinghistoryservice.usecase.GetTransactionByIdUseCase;
import com.example.bankinghistoryservice.usecase.GetTransactionsByAccountIdUseCase;
import com.example.bankinghistoryservice.usecase.ProcessTransactionUseCase;
import com.example.bankinghistoryservice.usecase.UpdateTransactionStatusUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final CreateTransactionUseCase createTransactionUseCase;
    private final GetTransactionByIdUseCase getTransactionByIdUseCase;
    private final GetTransactionsByAccountIdUseCase getTransactionsByAccountIdUseCase;
    private final UpdateTransactionStatusUseCase updateTransactionStatusUseCase;
    private final ProcessTransactionUseCase processTransactionUseCase;

    @Autowired
    public TransactionController(
        CreateTransactionUseCase createTransactionUseCase,
        GetTransactionByIdUseCase getTransactionByIdUseCase,
        GetTransactionsByAccountIdUseCase getTransactionsByAccountIdUseCase,
        UpdateTransactionStatusUseCase updateTransactionStatusUseCase,
        ProcessTransactionUseCase processTransactionUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
        this.getTransactionByIdUseCase = getTransactionByIdUseCase;
        this.getTransactionsByAccountIdUseCase = getTransactionsByAccountIdUseCase;
        this.updateTransactionStatusUseCase = updateTransactionStatusUseCase;
        this.processTransactionUseCase = processTransactionUseCase;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = transactionDTO.toDomain();
        Transaction createdTransaction = createTransactionUseCase.execute(transaction);
        TransactionDTO dto = TransactionDTO.fromDomain(createdTransaction);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable String id) {
        TransactionDTO transaction = getTransactionByIdUseCase.execute(id);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<Page<TransactionDTO>> getTransactionsByAccountId(
            @PathVariable String accountId,
            Pageable pageable
    ) {
        Page<Transaction> transactions = getTransactionsByAccountIdUseCase.execute(accountId, pageable);
        Page<TransactionDTO> transactionsDTOs = transactions.map(TransactionDTO::fromDomain);
        return ResponseEntity.ok(transactionsDTOs);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TransactionDTO> updateTransactionStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateTransactionStatusRequest request) {
        TransactionDTO updatedTransaction = updateTransactionStatusUseCase.execute(id, request);
        return ResponseEntity.ok(updatedTransaction);
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<Void> processTransaction(@PathVariable String id) {
        processTransactionUseCase.execute(id);
        // Return 202 Accepted, as processing might be asynchronous or complete immediately.
        return ResponseEntity.accepted().build();
    }

}