package com.bank.controllers;

import com.bank.services.AccountService;
import com.bank.services.PendingService;
import com.bank.Account;
import com.bank.Pending;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final PendingService pendingService;
    @Autowired
    AccountController(AccountService accountService,PendingService pendingService){
        this.accountService = accountService;
        this.pendingService = pendingService;
    }

    @PostMapping
    public Account addAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }
    @PutMapping("/{id}")
    public Account updateAccountById(@PathVariable String id,@RequestBody Account products){
        return accountService.updateAccount(id, products);
    }
    @PostMapping("/pay/{value}")
    public ResponseEntity<String> payAnything(@RequestBody Account account,@PathVariable float value){
        Account result = accountService.getAccountById(account.getNumber());
        System.out.println(account.getNumber());
        if(result==null){
            return new ResponseEntity<>("Account not found",HttpStatus.NOT_FOUND);
        }
        Pending pending =pendingService.createPending(new Pending(value, result, false,false));
        System.out.println(""+pending.getId());
        return new ResponseEntity<>(""+pending.getId(),HttpStatus.OK);
    }
    @PostMapping("/motherlode/{value}")
    public Account addMoney(@RequestBody Account account,@PathVariable float value){
        Account result = accountService.getAccountById(account.getNumber());
        if(result!=null){
            result.setMoney(value+result.getMoney());
            System.out.println(result.getMoney());
            return accountService.updateAccount(account.getNumber(), result);
        }
        return null;
    }
    @DeleteMapping
    public void deleteAccountById(@PathVariable long id){
        accountService.deleteAccountById(id);
    }

}