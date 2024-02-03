package com.bank.repository;
import com.bank.Cheque;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
}