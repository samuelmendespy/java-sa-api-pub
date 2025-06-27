package com.example.coreservice.presentation.controller;

import com.example.coreservice.application.dto.account.*;
import com.example.coreservice.application.dto.transaction.CoreServiceTransactionResponse;
import com.example.coreservice.application.dto.transaction.DepositRequest;
import com.example.coreservice.application.dto.transaction.PaymentRequest;
import com.example.coreservice.application.dto.transaction.WithdrawRequest;
import com.example.coreservice.application.dto.user.CoreServiceUserResponse;
import com.example.coreservice.application.dto.user.UserCreationRequest;
import com.example.coreservice.application.dto.user.UserSelectionByIdRequest;
import com.example.coreservice.application.dto.user.UserUpdateRequest;

import com.example.coreservice.application.usecase.account.CreateAccountUseCase;
import com.example.coreservice.application.usecase.account.GetAccountBalanceUseCase;
import com.example.coreservice.application.usecase.account.GetAccountByIdUseCase;
import com.example.coreservice.application.usecase.account.UpdateAccountUseCase;
import com.example.coreservice.application.usecase.transaction.DepositMoneyUseCase;
import com.example.coreservice.application.usecase.transaction.MakePaymentUseCase;
import com.example.coreservice.application.usecase.transaction.WithdrawMoneyUseCase;
import com.example.coreservice.application.usecase.user.CreateUserUseCase;
import com.example.coreservice.application.usecase.user.GetUserByIdUseCase;
import com.example.coreservice.application.usecase.user.UpdateUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CoreController {

    private final GetUserByIdUseCase getUserByIdUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final GetAccountBalanceUseCase getAccountBalanceUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final MakePaymentUseCase makePaymentUseCase;
    private final WithdrawMoneyUseCase withdrawMoneyUseCase;
    private final DepositMoneyUseCase depositMoneyUseCase;

    public CoreController(
            GetUserByIdUseCase getUserByIdUseCase,
            CreateUserUseCase createUserUseCase,
            UpdateUserUseCase updateUserUseCase,
            CreateAccountUseCase createAccountUseCase,
            GetAccountByIdUseCase getAccountByIdUseCase,
            GetAccountBalanceUseCase getAccountBalanceUseCase,
            UpdateAccountUseCase updateAccountUseCase,
            MakePaymentUseCase makePaymentUseCase,
            WithdrawMoneyUseCase withdrawMoneyUseCase,
            DepositMoneyUseCase depositMoneyUseCase) {
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.createAccountUseCase = createAccountUseCase;
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.getAccountBalanceUseCase = getAccountBalanceUseCase;
        this.updateAccountUseCase = updateAccountUseCase;
        this.makePaymentUseCase = makePaymentUseCase;
        this.withdrawMoneyUseCase = withdrawMoneyUseCase;
        this.depositMoneyUseCase = depositMoneyUseCase;
    }

    @PostMapping("/users")
    public Mono<ResponseEntity<CoreServiceUserResponse>> createUser(@RequestBody UserCreationRequest request) {
        return createUserUseCase.execute(request)
                .map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.CREATED))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build())); // Basic error handling
    }

    @GetMapping("/users/{id}")
    public Mono<ResponseEntity<CoreServiceUserResponse>> getUserById(@PathVariable String id, @RequestBody UserSelectionByIdRequest request) {
        return getUserByIdUseCase.execute(request)
                .map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.OK))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @PutMapping("/users/{id}")
    public Mono<ResponseEntity<CoreServiceUserResponse>> updateUser(@PathVariable String id, @RequestBody UserUpdateRequest request) {
        return updateUserUseCase.execute(request)
                .map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.OK))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @PostMapping("/accounts")
    public Mono<ResponseEntity<CoreServiceAccountResponse>> createAccount(@RequestBody AccountCreationRequest request) {
        return createAccountUseCase.execute(request)
                .map(accountResponse -> new ResponseEntity<>(accountResponse, HttpStatus.OK))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build())); // Basic error handling
    }

    @GetMapping("/accounts/{id}")
    public Mono<ResponseEntity<CoreServiceAccountResponse>> getAccountById(
            @PathVariable String id,
            @RequestBody AccountSelectionByIdRequest request) {
        return getAccountByIdUseCase.execute(request)
                .map(accountResponse -> new ResponseEntity<>(accountResponse, HttpStatus.OK))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @GetMapping("/accounts/{id}/balance")
    public Mono<ResponseEntity<AccountBalanceResponse>> getAccountBalance(
            @PathVariable String id,
            @RequestBody AccountBalanceRequest request) {
        return getAccountBalanceUseCase.execute(request)
                .map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.OK))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @PutMapping("/accounts/{id}")
    public Mono<ResponseEntity<AccountUpdateResponse>> updateAccount(@PathVariable String id, @RequestBody AccountUpdateRequest request) {
        return updateAccountUseCase.execute(request)
                .map(accountResponse -> new ResponseEntity<>(accountResponse, HttpStatus.OK))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @PostMapping("/payments")
    public Mono<ResponseEntity<CoreServiceTransactionResponse>> makePayment(@RequestBody PaymentRequest request) {
        return makePaymentUseCase.execute(request)
                .map(transactionResponse -> new ResponseEntity<>(transactionResponse, HttpStatus.OK))
                .onErrorResume(e -> {
                    if (e instanceof org.springframework.web.server.ResponseStatusException) {
                        return Mono.just(ResponseEntity.status(((org.springframework.web.server.ResponseStatusException) e).getStatusCode())
                                .body(null)); // Return null body for 204
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }

    @PostMapping("/withdrawals")
    public Mono<ResponseEntity<CoreServiceTransactionResponse>> withdrawMoney(@RequestBody WithdrawRequest request) {
        return withdrawMoneyUseCase.execute(request)
                .map(transactionResponse -> new ResponseEntity<>(transactionResponse, HttpStatus.OK))
                .onErrorResume(e -> {
                    if (e instanceof org.springframework.web.server.ResponseStatusException) {
                        return Mono.just(ResponseEntity.status(((org.springframework.web.server.ResponseStatusException) e).getStatusCode())
                                .body(null)); // Return null body for 204
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }

    @PostMapping("/deposits")
    public Mono<ResponseEntity<CoreServiceTransactionResponse>> depositMoney(@RequestBody DepositRequest request) {
        return depositMoneyUseCase.execute(request)
                .map(transactionResponse -> new ResponseEntity<>(transactionResponse, HttpStatus.OK))
                .onErrorResume(e -> {
                    if (e instanceof org.springframework.web.server.ResponseStatusException) {
                        return Mono.just(ResponseEntity.status(((org.springframework.web.server.ResponseStatusException) e).getStatusCode())
                                .body(null)); // Return null body for 204
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }
}
