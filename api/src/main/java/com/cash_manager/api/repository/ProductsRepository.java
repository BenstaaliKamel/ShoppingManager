package com.cash_manager.api.repository;
import com.cash_manager.api.Products;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}