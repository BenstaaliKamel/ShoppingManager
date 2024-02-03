package com.cash_manager.api.services;

import com.cash_manager.api.Products;
import java.util.List;

public interface ProductsService {
    List<Products> getAllProducts();
    Products getProductsById(Long id);
    Products createProducts(Products user);
    Products updateProducts(long id,Products products);
    void deleteProductsById(Long id);
    void reduceProducts(Long id,int quantity);
    // Other service methods
}
