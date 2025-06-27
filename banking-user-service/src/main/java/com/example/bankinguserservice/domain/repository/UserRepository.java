package com.example.bankinguserservice.domain.repository;

import com.example.bankinguserservice.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByCpf(String cpf);
}