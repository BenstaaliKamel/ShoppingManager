package com.cash_manager.api.controllers;

import com.cash_manager.api.services.ProductsService;
import com.cash_manager.api.services.PanierService;
import com.cash_manager.api.Products;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;
    private final PanierService panierService;
    @Autowired
    ProductsController(ProductsService productsService,PanierService panierService){
        this.productsService = productsService;
        this.panierService=panierService;
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<Products>> getAllProducts() {
        return new ResponseEntity<>(productsService.getAllProducts(),HttpStatus.OK);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductsById(@PathVariable long id) {
        Products p = productsService.getProductsById(id);
        if(p==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(p,HttpStatus.OK);

        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Products> addProducts(@RequestBody Products Products) {

        return new ResponseEntity<>(productsService.createProducts(Products),HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProductsById(@PathVariable long id,@RequestBody Products products){

        return new ResponseEntity<Products>(productsService.updateProducts(id, products),HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductsById(@PathVariable long id){
        if(productsService.getProductsById(id)==null){
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
        }
        panierService.deletePanierByProductsId(id);
        productsService.deleteProductsById(id);
        return new ResponseEntity<>("Element succesfully deleted",HttpStatus.OK);
    }

}