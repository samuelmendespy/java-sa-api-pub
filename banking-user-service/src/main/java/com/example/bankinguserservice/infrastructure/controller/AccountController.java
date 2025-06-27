package com.example.bankinguserservice.infrastructure.controller;

import com.example.bankinguserservice.domain.model.Account;
import com.example.bankinguserservice.infrastructure.dto.*;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import com.example.bankinguserservice.usecase.account.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final GetAllAccountsUseCase getAllAccountsUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;

    public AccountController(
            GetAccountByIdUseCase getAccountByIdUseCase,
            GetAllAccountsUseCase getAllAccountsUseCase,
            CreateAccountUseCase createAccountUseCase,
            UpdateAccountUseCase updateAccountUseCase,
            DeleteAccountUseCase deleteAccountUseCase
    ) {
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.getAllAccountsUseCase = getAllAccountsUseCase;
        this.createAccountUseCase = createAccountUseCase;
        this.updateAccountUseCase = updateAccountUseCase;
        this.deleteAccountUseCase = deleteAccountUseCase;
    }

    @Operation(summary = "Create a new account", description = "Creates a new bank account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(
            @RequestParam(name="userId") String userId,
            @RequestBody AccountCreationRequestDTO request
    ) {
        try {
            Account createdAccount = createAccountUseCase.execute(userId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(AccountDTO.fromDomain(createdAccount));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get an account by ID", description = "Retrieve the details of an existing account by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AccountSelectionResponseDTO> getAccountById(@PathVariable String id) {
        try {
            AccountSelectionResponseDTO account = getAccountByIdUseCase.execute(id);
            return ResponseEntity.ok(account);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get all accounts", description = "Retrieve a list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of accounts retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<AccountSelectionResponseDTO>> getAllAccounts(Pageable pageable) {
        try {
            Page<AccountSelectionResponseDTO> accounts = getAllAccountsUseCase.execute(pageable);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update an existing account", description = "Update the details of an existing account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable String id, @RequestBody AccountUpdateRequestDTO request) {
        try {
            Account updatedAccount = updateAccountUseCase.execute(id, request);
            return ResponseEntity.ok(AccountDTO.fromDomain(updatedAccount));
        } catch (NotFoundException e) {
            log.warn("Account not found for ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error updating account with ID: {}", id, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Delete an account by ID", description = "Delete an account and its associated cards by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Account deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable String id,
            @RequestBody AccountDeletionRequestDTO request) {
        try {
            deleteAccountUseCase.execute(request);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotFoundException e) {
            log.warn("Account not found for ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error updating account with ID: {}", id, e);
            return ResponseEntity.badRequest().build();
        }
    }
}