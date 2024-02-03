package com.cash_manager.api;


public class Cheque {
     /**
     * Unique identifier for the cheque.
     */
    private long id;
      /**
     * Value of the cheque.
     */
    private int value;
        /**
     * The associated payment account.
     */
    private Payment account;
       /**
     * Constructor to initialize the cheque with specified values.
     * param id The unique identifier for the cheque.
     * param account The associated payment account.
     * param value The value of the cheque.
     */
    public Cheque(long id,Payment account,int value){
        this.id=id;
        this.account=account;
        this.value=value;
    }
    /**
     * Gets the unique identifier of the cheque.
     * return The cheque's unique identifier.
     */
    public long getId() {
        return id;
    }
       /**
     * Sets the unique identifier of the cheque.
     * param id The new unique identifier for the cheque.
     */
    public void setId(long id) {
        this.id = id;
    }
     /**
     * Gets the value of the cheque.
     * return The value of the cheque.
     */
    public int getValue() {
        return value;
    }
     /**
     * Sets the value of the cheque.
     * param value The new value of the cheque.
     */
    public void setValue(int value) {
        this.value = value;
    }
     /**
     * Gets the associated payment account.
     * return The associated payment account.
     */
    public Payment getAccount() {
        return account;
    }
    /**
     * Sets the associated payment account.
     * param account The new associated payment account.
     */
    public void setAccount(Payment account) {
        this.account = account;
    }
}
