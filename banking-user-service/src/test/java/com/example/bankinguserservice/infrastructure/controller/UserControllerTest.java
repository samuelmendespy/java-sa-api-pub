package com.example.bankinguserservice.infrastructure.controller;

import com.example.bankinguserservice.domain.model.Account;
import com.example.bankinguserservice.domain.model.User;
import com.example.bankinguserservice.infrastructure.dto.UserCreationRequestDTO;
import com.example.bankinguserservice.infrastructure.dto.UserUpdateRequestDTO;
import com.example.bankinguserservice.infrastructure.exception.NotFoundException;
import com.example.bankinguserservice.infrastructure.exception.UserAlreadyExistsException;
import com.example.bankinguserservice.usecase.user.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetUserByIdUseCase getUserByIdUseCase;
    @MockBean
    private GetAllUsersUseCase getAllUsersUseCase;
    @MockBean
    private CreateUserUseCase createUserUseCase;
    @MockBean
    private UpdateUserUseCase updateUserUseCase;
    @MockBean
    private DeleteUserUseCase deleteUserUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private final String baseUrl = "/users";

    private User mockUser() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setCpf("12345678901");
        user.setName("John Doe");
        user.setAccount(new Account());
        return user;
    }

    @Test
    @DisplayName("GET /users/{id} - should return user when found")
    void getUserById_success() throws Exception {
        User user = mockUser();
        Mockito.when(getUserByIdUseCase.execute(anyString())).thenReturn(user);

        mockMvc.perform(get(baseUrl + "/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value("12345678901"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    @DisplayName("GET /users/{id} - should return 404 when not found")
    void getUserById_notFound() throws Exception {
        Mockito.when(getUserByIdUseCase.execute(anyString())).thenThrow(new NotFoundException("User not found"));

        mockMvc.perform(get(baseUrl + "/invalid-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /users - should return paginated users")
    void getAllUsers_success() throws Exception {
        User user = mockUser();
        Mockito.when(getAllUsersUseCase.execute(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(user)));

        mockMvc.perform(get(baseUrl).param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].cpf").value("12345678901"));
    }

    @Test
    @DisplayName("POST /users - should create user")
    void createUser_success() throws Exception {
        User user = mockUser();
        UserCreationRequestDTO dto = new UserCreationRequestDTO();
        dto.setCpf(user.getCpf());
        dto.setName(user.getName());

        Mockito.when(createUserUseCase.execute(any())).thenReturn(user);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").value(user.getCpf()))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    @DisplayName("POST /users - should return 409 when user already exists")
    void createUser_conflict() throws Exception {
        UserCreationRequestDTO dto = new UserCreationRequestDTO();
        dto.setCpf("12345678901");
        dto.setName("John Doe");

        Mockito.when(createUserUseCase.execute(any()))
                .thenThrow(new UserAlreadyExistsException("User already exists"));

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("PUT /users/{id} - should update user")
    void updateUser_success() throws Exception {
        User user = mockUser();
        UserUpdateRequestDTO dto = new UserUpdateRequestDTO();
        dto.setId(user.getId());
        dto.setCpf(user.getCpf());
        dto.setName("Updated Name");
        dto.setAccount(user.getAccount());

        user.setName("Updated Name");
        Mockito.when(updateUserUseCase.execute(any())).thenReturn(user);

        mockMvc.perform(put(baseUrl + "/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    @DisplayName("PUT /users/{id} - should return 404 if ID is -1")
    void updateUser_invalidId() throws Exception {
        UserUpdateRequestDTO dto = new UserUpdateRequestDTO();
        dto.setId("-1");
        dto.setCpf("12345678901");
        dto.setName("Someone");

        mockMvc.perform(put(baseUrl + "/-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /users/{id} - should delete user")
    void deleteUser_success() throws Exception {
        Mockito.doNothing().when(deleteUserUseCase).execute(anyString());

        mockMvc.perform(delete(baseUrl + "/some-id"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /users/{id} - should return 404 if not found")
    void deleteUser_notFound() throws Exception {
        Mockito.doThrow(new NotFoundException("User not found"))
                .when(deleteUserUseCase).execute(anyString());

        mockMvc.perform(delete(baseUrl + "/invalid-id"))
                .andExpect(status().isNotFound());
    }
}
