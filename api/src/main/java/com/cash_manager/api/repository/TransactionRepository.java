package com.cash_manager.api.repository;

import com.cash_manager.api.Transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

    public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Custom query methods if needed
    List<Transaction> getByUserId(long id);
}