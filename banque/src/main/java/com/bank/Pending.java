package com.bank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table
public class Pending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float ammount;
    private boolean isConfirmed;
    private boolean isRefused;
    @ManyToOne
    @JsonIgnoreProperties({"SecurityNumber","expiration","name"})
    private Account account;
    public Pending(float ammount,Account account,boolean isConfirmed,boolean isRefused){
        this.ammount=ammount;
        this.account=account;
        this.isConfirmed=isConfirmed;
        this.isRefused=isRefused;


    }
    public Pending(){
        
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public float getAmmount() {
        return ammount;
    }
    public void setAmmount(float ammount) {
        this.ammount = ammount;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public boolean isConfirmed() {
        return isConfirmed;
    }
    public void setConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
    public boolean isRefused() {
        return isRefused;
    }
    public void setRefused(boolean isRefused) {
        this.isRefused = isRefused;
    }

}
