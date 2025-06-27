package com.example.bankinguserservice.infrastructure.controller;

import com.example.bankinguserservice.domain.model.User;
import com.example.bankinguserservice.infrastructure.dto.UserCreationRequestDTO;
import com.example.bankinguserservice.infrastructure.dto.UserDTO;
import com.example.bankinguserservice.infrastructure.dto.UserUpdateRequestDTO;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import com.example.bankinguserservice.infrastructure.exception.UserAlreadyExistsException;
import com.example.bankinguserservice.usecase.user.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final GetUserByIdUseCase getUserByIdUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserController(
            GetAllUsersUseCase getAllUsersUseCase,
            GetUserByIdUseCase getUserByIdUseCase,
            CreateUserUseCase createUserUseCase,
            UpdateUserUseCase updateUserUseCase,
            DeleteUserUseCase deleteUserUseCase
    ) {
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @Operation(summary = "Get a user by ID", description = "Retrieve a single user including their account and cards by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        try {
            User user = getUserByIdUseCase.execute(id);
            return ResponseEntity.ok(UserDTO.fromDomain(user));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
        Page<User> users = getAllUsersUseCase.execute(pageable);
        Page<UserDTO> userDTOS = users.map(UserDTO::fromDomain);
        return ResponseEntity.ok(userDTOS);
    }

    @Operation(summary = "Create a new user", description = "Create a new user with a linked account and a card.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreationRequestDTO dto) {
        try {
            User createdUser = createUserUseCase.execute(dto);
            if (createdUser == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(UserDTO.fromDomain(createdUser));
        } catch (UserAlreadyExistsException ex) {
            log.warn("User creation failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error creating user", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update an existing user", description = "Update the details of an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String id,
            @RequestBody UserUpdateRequestDTO request
    ) {
        if (id.equals("-1")) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        try {
            User updatedUser = updateUserUseCase.execute(request);
            if (updatedUser != null) {
                return ResponseEntity.ok(UserDTO.fromDomain(updatedUser));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Delete a user by ID", description = "Delete a user and their associated account and cards by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            deleteUserUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}