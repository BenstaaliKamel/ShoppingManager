package com.cash_manager.api.repository;

import com.cash_manager.api.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"transactions","panier","payment"})
    Optional<User> findByEmail(String email);
    @EntityGraph(attributePaths = {"transactions","panier","payment"})
    List<User> findAll();
    @EntityGraph(attributePaths = {"transactions","panier","payment"})
    Optional<User> findById(Long id);
}