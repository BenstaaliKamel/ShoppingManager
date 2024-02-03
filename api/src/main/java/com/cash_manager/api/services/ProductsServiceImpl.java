package com.cash_manager.api.services;

import com.cash_manager.api.Products;
import com.cash_manager.api.repository.ProductsRepository;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }
    

    @Override
    public Products getProductsById(Long id) {
        return productsRepository.findById(id).orElse(null);
    }

    @Override
    public Products createProducts(Products products) {
        // Add any business logic or validation before saving the products
        return productsRepository.save(products);
    }

    @Override
    public Products updateProducts(long id,Products products){
        Products p = productsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product id not found"));
        p.setName(products.getName());
        p.setPrice(products.getPrice());
        return productsRepository.save(p);
    }

    @Override
    public void deleteProductsById(Long id){
        productsRepository.deleteById(id);
    }

    public void reduceProducts(Long id , int quantity){
        Products p = getProductsById(id);
        p.setQuantity(p.getQuantity()-quantity);
        productsRepository.save(p);
    }
    // Implement other service methods based on your application's requirements
}