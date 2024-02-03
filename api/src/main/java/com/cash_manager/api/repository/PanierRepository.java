package com.cash_manager.api.repository;
import com.cash_manager.api.Panier;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

    
public interface PanierRepository extends JpaRepository<Panier, Long> {
    // Custom query methods if needed
    List<Panier> getByUserId(long id);
    @Transactional
    long deleteByUserId(Long user_id);
    long deleteByProductsId(Long user_id);
}

