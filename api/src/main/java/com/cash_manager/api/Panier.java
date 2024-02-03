package com.cash_manager.api;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table
public class Panier {
       /**
     * Unique identifier for the shopping cart.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
      /**
     * Quantity of products in the shopping cart.
     */
    private int quantity;
     /**
     * The associated product in the shopping cart.
     * FetchType.EAGER is used for eager loading of the associated product.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "products_id")
    private Products products;
        /**
     * The user who owns the shopping cart.
     * FetchType.EAGER is used for eager loading of the associated user.
     * Certain properties of the user are ignored during JSON serialization.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"nom","email","password","admin","enabled","username","authorities","accountNonExpired","accountNonLocked","credentialsNonExpired"})
    @JoinColumn(name = "user_id")
    private User user;
       /**
     * Gets the quantity of products in the shopping cart.
     * return The quantity of products.
     */
    public int getQuantity() {
        return quantity;
    }
      /**
     * Sets the quantity of products in the shopping cart.
     * param quantity The new quantity of products.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Gets the unique identifier of the shopping cart.
     * return The shopping cart's unique identifier.
     */
    public long getId() {
        return id;
    }
       /**
     * Sets the unique identifier of the shopping cart.
     * param id The new unique identifier for the shopping cart.
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * Sets the associated product in the shopping cart.
     * param products The product to set in the shopping cart.
     */
    public void setProducts(Products products) {
        this.products = products;
    }
      /**
     * Gets the associated product in the shopping cart.
     * return The product in the shopping cart.
     */
    public Products getProducts() {
        return products;
    }
     /**
     * Sets the user who owns the shopping cart.
     * param user The user who owns the shopping cart.
     */
    public void setUser(User user) {
        this.user = user;
    }
       /**
     * Gets the user who owns the shopping cart.
     * return The user who owns the shopping cart.
     */
    public User getUser() {
        return user;
    }
    
}