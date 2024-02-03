package com.cash_manager.api;

import java.util.Date;

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
public class Payment {
     /**
     * Unique identifier for the payment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
     /**
     * Payment card number.
     */
    private String number;
    /**
     * Cardholder name.
     */
    private String name;
    /**
     * Flag indicating whether the payment is pending.
     */
    private Date expiration;
     /**
     * Identifier for the pending payment (if applicable).
     */
    private boolean pending;
     /**
     * The user associated with the payment.
     * FetchType.EAGER is used for eager loading of the associated user.
     * Certain properties of the user are ignored during JSON serialization.
     */
    private long pendingId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"nom","email","password","admin","enabled","username","authorities","accountNonExpired","accountNonLocked","credentialsNonExpired"})
    @JoinColumn(name = "user_id")
    private User user;
      /**
     * Gets the cardholder name.
     * return The cardholder name.
     */
    public String getName() {
        return name;
    }
      /**
     * Sets the cardholder name.
     * param name The new cardholder name.
     */
    public void setName(String name) {
        this.name = name;
    }
       /**
     * Gets the unique identifier of the payment.
     * return The payment's unique identifier.
     */
    public long getId() {
        return id;
    }
     /**
     * Sets the unique identifier of the payment.
     * param id The new unique identifier for the payment.
     */
    public void setId(long id) {
        this.id = id;
    }
       /**
     * Gets the user associated with the payment.
     * return The user associated with the payment.
     */
    public User getUser() {
        return user;
    }
    /**
     * Sets the user associated with the payment.
     * param user The user to associate with the payment.
     */
    public void setUser(User user) {
        this.user = user;
    }
      /**
     * Gets the expiration date of the payment card.
     * return The expiration date of the payment card.
     */
    public Date getExpiration() {
        return expiration;
    }
      /**
     * Gets the payment card number.
     * return The payment card number.
     */
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
      /**
     * Sets the payment card number.
     * param number The new payment card number.
     */
    public String getNumber() {
        return number;
    }
      /**
     * Checks if the payment is pending.
     * return {code true} if the payment is pending, {code false} otherwise.
     */
    public void setNumber(String number) {
        this.number = number;
    }
      /**
     * Sets the pending status of the payment.
     * param pending The new pending status.
     */
    public boolean isPending() {
        return pending;
    }
      /**
     * Gets the identifier for the pending payment.
     * return The identifier for the pending payment.
     */
    public void setPending(boolean pending) {
        this.pending = pending;
    }
    
    /**
     * Sets the identifier for the pending payment.
     * param pendingId The new identifier for the pending payment.
     */
    public long getPendingId() {
        return pendingId;
    }
    public void setPendingId(long pendingId) {
        this.pendingId = pendingId;
    }
    
    
}
