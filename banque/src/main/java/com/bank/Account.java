package com.bank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Account{
    @Id
    private String number;
    private String name;
    private Date expiration;
    private int securityNumber;
    private float money;
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Cheque> cheque= new ArrayList<Cheque>();
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Pending> pending= new ArrayList<Pending>();
    public Account(){
        number="";
        expiration=new Date();
        securityNumber = 0;
        money = 0;
    }
    public Account(String number,Date expiration,int securityNumber,float money){
        this.expiration=expiration;
        this.number=number;
        this.securityNumber=securityNumber;
        this.money=money;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getExpiration() {
        return expiration;
    }
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
    public int getSecurityNumber() {
        return securityNumber;
    }
    public void setSecurityNumber(int securityNumber) {
        this.securityNumber = securityNumber;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public float getMoney() {
        return money;
    }
    public void setMoney(float money) {
        this.money = money;
    }
}
