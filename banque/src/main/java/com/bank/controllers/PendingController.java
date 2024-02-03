package com.bank.controllers;

import com.bank.services.AccountService;
import com.bank.services.PendingService;
import com.bank.Account;
import com.bank.Pending;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/pending")
public class PendingController {

    private final PendingService pendingService;
    private final AccountService accountService;
    @Autowired
    PendingController(PendingService pendingService,AccountService accountService){
        this.pendingService = pendingService;
        this.accountService= accountService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> confirmPending(@PathVariable long id,@RequestBody Account account) {
        Account a = accountService.getAccountById(account.getNumber());
        Pending pending = pendingService.getPendingById(id);
        if(pending == null){
            return new ResponseEntity<>("Request not found",HttpStatus.NOT_FOUND);
        }
        if(pending.isConfirmed() || pending.isRefused()){
            return new ResponseEntity<>("Request refused",HttpStatus.FORBIDDEN);
        }
        if(a == null || !a.getNumber().equals(account.getNumber())  || a.getSecurityNumber()!=account.getSecurityNumber() || a.getExpiration().getTime() != account.getExpiration().getTime()){
            return new ResponseEntity<>("Bad request",HttpStatus.BAD_REQUEST);
        }
        if(a.getMoney()<pending.getAmmount()){
            pendingService.updatePending(id, false,true);
            return new ResponseEntity<>("Payment couldn't be done",HttpStatus.BAD_REQUEST);
        }
        pendingService.updatePending(id,true,false);
        accountService.creditAccount(a, a, pending.getAmmount());
        return new ResponseEntity<>("Payment has been confirmed",HttpStatus.OK);
        
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> isPendingConfirmed(@PathVariable long id){
        Pending pending=pendingService.getPendingById(id);
        if(pending==null){
            return new ResponseEntity<>("Payment isn't being processed",HttpStatus.NOT_FOUND);
        }
        if(pending.isRefused()){
            return new ResponseEntity<>("Payment refused",HttpStatus.BAD_REQUEST);
        }
        if(!pending.isConfirmed()){
            return new ResponseEntity<>("Payment is pending",HttpStatus.ACCEPTED);
        }
        
        return new ResponseEntity<>("Payment is done",HttpStatus.OK);
        
    }

}