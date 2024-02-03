package com.bank.repository;
import com.bank.Pending;


import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingRepository extends JpaRepository<Pending, Long> {
}