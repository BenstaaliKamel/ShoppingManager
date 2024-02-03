package com.bank.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.Account;
import com.bank.Cheque;
import com.bank.services.AccountService;
import com.bank.services.ChequeService;

@RestController
@RequestMapping("/api/cheque")
public class ChequeControlleur {
    @Autowired
    ChequeService chequeService;
    @Autowired
    AccountService accountService;
    @PostMapping("/value")
    public ResponseEntity<String> getValueCheque(@RequestBody Cheque cheque){
        if(cheque == null){
            return new ResponseEntity<>("Bad request",HttpStatus.BAD_REQUEST);
        }
        Cheque element = chequeService.getChequeById(cheque.getId());
        if(element == null ){
            return new ResponseEntity<>("Cheque Not found",HttpStatus.NOT_FOUND);
        }
        if(!cheque.getAccount().getNumber().equals(element.getAccount().getNumber())){
            return new ResponseEntity<>("Data is not coherent",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(""+element.getValue(),HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Cheque>>  getAllCheque(){
        return new ResponseEntity<>(chequeService.getAllCheque(),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> addCheque(@RequestBody Cheque cheque){
        return chequeService.createCheque(cheque);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> useCheque(@PathVariable long id){
        Cheque cheque = chequeService.getChequeById(id);
        Account result = accountService.getAccountById(cheque.getAccount().getNumber());
        ResponseEntity<String> answer = accountService.creditAccount(result, cheque.getAccount(), cheque.getValue());
        if(answer.getStatusCode()==HttpStatus.OK){
            chequeService.deleteChequeById(cheque.getId());
        }
        return answer;
    }
    @DeleteMapping
    public ResponseEntity<String> deletecheque(@RequestBody long id){
        if(chequeService.getChequeById(id)!=null){
            return new ResponseEntity<>("Cheque successfully deleted",HttpStatus.OK);
            
        }else{
            return new ResponseEntity<>("Cheque Not found",HttpStatus.NOT_FOUND);
        }
    }
}
