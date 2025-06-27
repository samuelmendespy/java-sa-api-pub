package com.example.bankinguserservice.infrastructure.controller;

import com.example.bankinguserservice.domain.model.Card;
import com.example.bankinguserservice.infrastructure.dto.CardCreationRequestDTO;
import com.example.bankinguserservice.infrastructure.dto.CardDTO;
import com.example.bankinguserservice.infrastructure.dto.CardUpdateRequestDTO;
import com.example.bankinguserservice.usecase.card.*;
import org.springframework.data.domain.Page;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final GetAllCardsUseCase getAllCardsUseCase;
    private final GetCardByIdUseCase getCardByIdUseCase;
    private final CreateCardUseCase createCardUseCase;
    private final UpdateCardUseCase updateCardUseCase;
    private final DeleteCardUseCase deleteCardUseCase;
    private final GetCardsByAccountIdUseCase getCardsByAccountIdUseCase;

    public CardController(
            GetAllCardsUseCase getAllCardsUseCase,
            GetCardByIdUseCase getCardByIdUseCase,
            CreateCardUseCase createCardUseCase,
            UpdateCardUseCase updateCardUseCase,
            DeleteCardUseCase deleteCardUseCase,
            GetCardsByAccountIdUseCase getCardsByAccountIdUseCase
    ) {
        this.getAllCardsUseCase = getAllCardsUseCase;
        this.getCardsByAccountIdUseCase = getCardsByAccountIdUseCase;
        this.createCardUseCase = createCardUseCase;
        this.updateCardUseCase = updateCardUseCase;
        this.deleteCardUseCase = deleteCardUseCase;
        this.getCardByIdUseCase = getCardByIdUseCase;
    }

    @Operation(summary = "Get a card by ID", description = "Retrieve a single card by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardDTO.class))),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable String id) {
        try {
            Card card = getCardByIdUseCase.execute(id);
            return ResponseEntity.ok(CardDTO.fromDomain(card));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get all cards", description = "Retrieve a list of all cards.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of cards retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<CardDTO>> getAllCards(Pageable pageable) {
        try {
            Page<Card> cards = getAllCardsUseCase.execute(pageable);
            Page<CardDTO> cardDTOS = cards.map(CardDTO::fromDomain);
            return ResponseEntity.ok(cardDTOS);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @Operation(summary = "Get cards by Account ID", description = "Retrieve a list of cards associated with a specific account ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of cards for the given account ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardDTO.class)))
    })
    @GetMapping("/by-account/{accountId}")
    public ResponseEntity<List<CardDTO>> getCardsByAccountId(@PathVariable String accountId) {

        List<Card> cards = getCardsByAccountIdUseCase.execute(accountId);

        if (cards.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        List<CardDTO> cardDTOS = cards.stream()
                .map(CardDTO::fromDomain)
                .collect(Collectors.toList());

        return ResponseEntity.ok(cardDTOS);
    }


    @Operation(summary = "Create a new card", description = "Creates a new bank card.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CardCreationRequestDTO request) {
        try {
            Card createdCard = createCardUseCase.execute(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(CardDTO.fromDomain(createdCard));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update an existing card", description = "Update the details of an existing card.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardDTO.class))),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CardDTO> updateCard(
            @PathVariable String id,
            @RequestBody CardUpdateRequestDTO request
            ) {
        try {
            Card updatedCard = updateCardUseCase.execute(id, request);

            return ResponseEntity.ok(CardDTO.fromDomain(updatedCard));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Delete a card by ID", description = "Delete a card by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Card deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable String id, @RequestBody String accountId) {
        try {
            deleteCardUseCase.execute(id, accountId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}