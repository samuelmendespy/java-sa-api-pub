package com.example.bankingjournalservice.infrastructure.controller;

import com.example.bankingjournalservice.domain.model.News;
import com.example.bankingjournalservice.infrastructure.dto.NewsDTO;
import com.example.bankingjournalservice.infrastructure.exception.NotFoundException;
import com.example.bankingjournalservice.usecase.news.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final GetNewsByIdUseCase getNewsByIdUseCase;
    private final GetAllNewsUseCase getAllNewsUseCase;
    private final CreateNewsUseCase createNewsUseCase;
    private final UpdateNewsUseCase updateNewsUseCase;
    private final DeleteNewsUseCase deleteNewsUseCase;

    public NewsController(
            GetNewsByIdUseCase getNewsByIdUseCase,
            GetAllNewsUseCase getAllNewsUseCase,
            CreateNewsUseCase createNewsUseCase,
            UpdateNewsUseCase updateNewsUseCase,
            DeleteNewsUseCase deleteNewsUseCase
    ) {
        this.getNewsByIdUseCase = getNewsByIdUseCase;
        this.getAllNewsUseCase = getAllNewsUseCase;
        this.createNewsUseCase = createNewsUseCase;
        this.updateNewsUseCase = updateNewsUseCase;
        this.deleteNewsUseCase = deleteNewsUseCase;
    }

    @Operation(summary = "Create a new account", description = "Creates a new bank account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NewsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<NewsDTO> createNews(
            @RequestBody NewsDTO NewsDTO
    ) {
        try {
            News news = NewsDTO.toDomain();
            News createdNews = createNewsUseCase.execute(news);
            return ResponseEntity.status(HttpStatus.CREATED).body(NewsDTO.fromDomain(createdNews));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get an account by ID", description = "Retrieve the details of an existing account by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NewsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Long id) {
        try {
            News account = getNewsByIdUseCase.execute(id);
            return ResponseEntity.ok(NewsDTO.fromDomain(account));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get all accounts", description = "Retrieve a list of all news.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of accounts retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NewsDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<NewsDTO>> getAllNews(Pageable pageable) {
        Page<News> news = getAllNewsUseCase.execute(pageable);
        Page<NewsDTO> newsDTOS = news.map(NewsDTO::fromDomain);
        return ResponseEntity.ok(newsDTOS);
    }

    @Operation(summary = "Update an existing account", description = "Update the details of an existing account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NewsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Long id, @RequestBody NewsDTO accountDTO) {
        try {
            News news = accountDTO.toDomain();
            News updatedAccount = updateNewsUseCase.execute(id, news);
            return ResponseEntity.ok(NewsDTO.fromDomain(updatedAccount));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
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
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        try {
            deleteNewsUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}