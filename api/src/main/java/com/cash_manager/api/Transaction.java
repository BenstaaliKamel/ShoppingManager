package com.cash_manager.api;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
public class Transaction {
        /**
     * Unique identifier for the transaction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * The user associated with the transaction.
     * FetchType.EAGER is used for eager loading of the associated user.
     * Certain properties of the user are ignored during JSON serialization.
     */

    @ManyToOne
    @JsonIgnoreProperties({"nom","email","password","admin","enabled","username","authorities","accountNonExpired","accountNonLocked","credentialsNonExpired"})
    @JoinColumn(name = "user_id")
    private User user;
     /**
     * Array of product IDs in the transaction.
     */
    private Long productId[];
    /**
     * Array of product quantities in the transaction.
     */
    private int productQuantity[];
     /**
     * Array of product prices in the transaction.
     */
    private float productPrices[];
     /**
     * Total price of the transaction.
     */
    private float totalPrices;
     /**
     * Gets the unique identifier of the transaction.
     * return The transaction's unique identifier.
     */
    public long getId() {
        return id;
    }
    /**
     * Sets the unique identifier of the transaction.
     * param id The new unique identifier for the transaction.
     */
    public void setId(long id) {
        this.id = id;
    }
     /**
     * Gets the array of product IDs in the transaction.
     * return The array of product IDs.
     */
    public Long[] getProductId() {
        return productId;
    }
     /**
     * Sets the array of product IDs in the transaction.
     * param productId The new array of product IDs.
     */
    public void setProductId(Long[] productId) {
        this.productId = productId;
    }
    /**
     * Gets the array of product quantities in the transaction.
     * return The array of product quantities.
     */
    public int[] getProductQuantity() {
        return productQuantity;
    }
     /**
     * Sets the array of product quantities in the transaction.
     * param productQuantity The new array of product quantities.
     */
    public void setProductQuantity(int[] productQuantity) {
        this.productQuantity = productQuantity;
    }
     /**
     * Gets the array of product prices in the transaction.
     * return The array of product prices.
     */
    public float[] getProductPrices() {
        return productPrices;
    }
     /**
     * Sets the array of product prices in the transaction.
     * param productPrices The new array of product prices.
     */
    public void setProductPrices(float[] productPrices) {
        this.productPrices = productPrices;
    }
     /**
     * Gets the total price of the transaction.
     * return The total price of the transaction.
     */
    public float getTotalPrices() {
        return totalPrices;
    }
      /**
     * Sets the total price of the transaction.
     * param totalPrices The new total price of the transaction.
     */
    public void setTotalPrices(float totalPrices) {
        this.totalPrices = totalPrices;
    }
        /**
     * Gets the user associated with the transaction.
     * return The user associated with the transaction.
     */

    public User getUser() {
        return user;
    }
      /**
     * Sets the user associated with the transaction.
     * param user The user to associate with the transaction.
     */
    public void setUser(User user) {
        this.user = user;
    }
    
}
