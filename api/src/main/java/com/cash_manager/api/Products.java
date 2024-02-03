package com.cash_manager.api;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Products {
     /**
     * Unique identifier for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * Name of the product.
     */
    private String name;
       /**
     * Price of the product.
     */
    private float price;
        /**
     * Quantity of the product.
     */
    private long quantity;
     /**
     * Image path of the product.
     */
    private String image;
     /**
     * List of shopping carts associated with the product.
     * Ignored during JSON serialization to avoid circular dependencies.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "products",cascade = CascadeType.ALL)
    private List<Panier> panier = new ArrayList<>();
     /**
     * Gets the unique identifier of the product.
     * return The product's unique identifier.
     */
    public long getId() {
        return id;
    }
      /**
     * Sets the unique identifier of the product.
     * param id The new unique identifier for the product.
     */
    public void setId(long id) {
        this.id = id;
    }
     /**
     * Gets the name of the product.
     * return The name of the product.
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the product.
     * param name The new name for the product.
     */
    public void setName(String name) {
        this.name = name;
    }
     /**
     * Gets the price of the product.
     * return The price of the product.
     */
    public float getPrice() {
        return price;
    }
    /**
     * Sets the price of the product.
     * param price The new price for the product.
     */
    public void setPrice(float price) {
        this.price = price;
    }
    
    /**
     * Gets the quantity of the product.
     * return The quantity of the product.
     */
    public long getQuantity() {
        return quantity;
    }
    /**
     * Sets the quantity of the product.
     * param quantity The new quantity for the product.
     */
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
    /**
    * Gets the list of shopping carts associated with the product.
    * return The list of shopping carts associated with the product.
    */
    public List<Panier> getPanier() {
        return panier;
    }
    /**
    * Sets the list of shopping carts associated with the product.
    * param panier The new list of shopping carts for the product.
    */
    public void setPanier(List<Panier> panier) {
        this.panier = panier;
    }
       /**
     * Gets the image path of the product.
     * return The image path of the product.
     */
    public String getImage() {
        return image;
    }
      /**
     * Sets the image path of the product.
     * param image The new image path for the product.
     */
    public void setImage(String image) {
        this.image = image;
    }
    
}
