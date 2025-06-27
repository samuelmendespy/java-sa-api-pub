package com.example.bankinguserservice.infrastructure.controller;

import com.example.bankinguserservice.infrastructure.dto.CardDeletionRequestDTO;
import com.example.bankinguserservice.infrastructure.dto.CardUpdateRequestDTO;
import com.example.bankinguserservice.domain.model.Card;
import com.example.bankinguserservice.infrastructure.dto.CardCreationRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.bankinguserservice.usecase.card.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CardController.class)
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private GetAllCardsUseCase getAllCardsUseCase;
    @MockBean private GetCardByIdUseCase getCardByIdUseCase;
    @MockBean private CreateCardUseCase createCardUseCase;
    @MockBean private UpdateCardUseCase updateCardUseCase;
    @MockBean private DeleteCardUseCase deleteCardUseCase;
    @MockBean private GetCardsByAccountIdUseCase getCardsByAccountIdUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private Card mockCard() {
        Card card = new Card();
        card.setId(UUID.randomUUID().toString());
        card.setNumber("1234-5678-9012-3456");
        card.setCreditLimit(BigDecimal.valueOf(1000));
        return card;
    }


    @Test
    @DisplayName("Should create card")
    void shouldCreateCard() throws Exception {
        Card card = mockCard();
        given(createCardUseCase.execute(any(CardCreationRequestDTO.class))).willReturn(card);

        CardCreationRequestDTO request = new CardCreationRequestDTO();
        request.setAccountId("acc-1");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.number").value(card.getNumber()));
    }

    @Test
    @DisplayName("Should get card by id")
    void shouldGetCardById() throws Exception {
        Card card = mockCard();
        given(getCardByIdUseCase.execute(anyString())).willReturn(card);

        mockMvc.perform(get("/cards/" + card.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(card.getId()));
    }

    @Test
    @DisplayName("Should update card")
    void shouldUpdateCard() throws Exception {
        Card card = mockCard();
        given(updateCardUseCase.execute(anyString(), any(CardUpdateRequestDTO.class))).willReturn(card);
        CardUpdateRequestDTO request = new CardUpdateRequestDTO();

        BigDecimal newCreditLimit = card.getCreditLimit();
        request.setAccountId("acc-1");
        request.setCreditLimit(newCreditLimit);

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/cards/" + card.getId())
                        .param("accountId", "acc-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value(card.getNumber()));
    }

    @Test
    @DisplayName("Should delete card")
    void shouldDeleteCard() throws Exception {

        CardDeletionRequestDTO request = new CardDeletionRequestDTO();
        request.setAccountId("acc-1");
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(delete("/cards/" + mockCard().getId())
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return empty list when no cards for account")
    void shouldReturnEmptyListWhenNoCardsForAccount() throws Exception {
        given(getCardsByAccountIdUseCase.execute(anyString())).willReturn(List.of());

        mockMvc.perform(get("/cards/by-account/account123"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
