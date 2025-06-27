package com.example.bankinguserservice.infrastructure.controller;

import com.example.bankinguserservice.domain.model.Account;
import com.example.bankinguserservice.domain.model.User;
import com.example.bankinguserservice.infrastructure.dto.*;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import com.example.bankinguserservice.usecase.account.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetAccountByIdUseCase getAccountByIdUseCase;
    @MockBean
    private GetAllAccountsUseCase getAllAccountsUseCase;
    @MockBean
    private CreateAccountUseCase createAccountUseCase;
    @MockBean
    private UpdateAccountUseCase updateAccountUseCase;
    @MockBean
    private DeleteAccountUseCase deleteAccountUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private User mockUser;
    private Account mockAccount;

    @BeforeEach
    public void setup() {

        mockUser = new User();
        mockUser.setId("usr123");
        mockUser.setCpf("12345678955");
        mockUser.setName("John Doe");
        mockUser.setAccount(null);

        mockAccount = new Account();
        mockAccount.setId("acc123");
        mockAccount.setNumber("000123");
        mockAccount.setAgency("0001");
        mockAccount.setBalance(BigDecimal.valueOf(1000));
        mockAccount.setOverdraftLimit(BigDecimal.valueOf(500));
        mockAccount.setCards(Collections.emptyList());
    }

    @Test
    public void shouldCreateAccount() throws Exception {
        when(createAccountUseCase.execute(any(), any())).thenReturn(mockAccount);
        AccountCreationRequestDTO request = new AccountCreationRequestDTO();
        request.setAgency("0001");
        request.setNumber("000123");
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
                        .param("userId", "usr123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void shouldReturnAccountById() throws Exception {
        when(getAccountByIdUseCase.execute("acc123"))
                .thenReturn(AccountSelectionResponseDTO.fromDomain(mockAccount));

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/acc123"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnNotFoundForInvalidAccountId() throws Exception {
        Mockito.doThrow(new NotFoundException("Account not found"))
                .when(getAccountByIdUseCase).execute("invalid");

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/invalid"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldUpdateAccount() throws Exception {
        when(updateAccountUseCase.execute(any(), any())).thenReturn(mockAccount);


        AccountUpdateRequestDTO request = new AccountUpdateRequestDTO();
        request.setId("acc123");
        request.setAgency("0001");
        request.setNumber("000123");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/accounts/acc123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldDeleteAccount() throws Exception {

        AccountDeletionRequestDTO request = new AccountDeletionRequestDTO();
        request.setAccountId("acc123");
        request.setUserId(Optional.of("usr123"));

        String jsonRequest = objectMapper.writeValueAsString(request);

        doNothing().when(deleteAccountUseCase).execute(request);

        mockMvc.perform(MockMvcRequestBuilders.delete("/accounts/acc123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldReturnBadRequestForCreateError() throws Exception {
        when(createAccountUseCase.execute(any(), any())).thenThrow(new RuntimeException("Error"));

        AccountCreationRequestDTO request = new AccountCreationRequestDTO();
        request.setAgency("0001");
        request.setNumber("000123");

        String jsonRequest = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
                        .param("userId", "user123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
