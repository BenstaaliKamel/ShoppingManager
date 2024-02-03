package com.cash_manager.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cash_manager.api.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> getByUserId(long id);
}

