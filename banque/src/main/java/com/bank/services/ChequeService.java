package com.bank.services;

import com.bank.Account;
import com.bank.Cheque;
import com.bank.repository.AccountRepository;
import com.bank.repository.ChequeRepository;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChequeService  {

    private final ChequeRepository checkRepository;
    private final AccountRepository accountRepository;
    public ChequeService(ChequeRepository checkRepository,AccountRepository accountRepository){
        this.checkRepository = checkRepository;
        this.accountRepository=accountRepository;
    }

    public List<Cheque> getAllCheque() {
        return checkRepository.findAll();
    }
    public Cheque getChequeById(Long id) {
        return checkRepository.findById(id).orElse(null);
    }
    public ResponseEntity<String> createCheque(Cheque check) {
        // Add any business logic or validation before saving the check
        
        if(check.getAccount()==null){
            return new ResponseEntity<>("Bad request , No data found",HttpStatus.BAD_REQUEST);
        }
        if(check.getValue()<0){
            return new ResponseEntity<>("Value of the check is negative",HttpStatus.BAD_REQUEST);
        }
        Account account = accountRepository.findByNumber(check.getAccount().getNumber()).orElse(null);
        if(account==null){
            return new ResponseEntity<>("Account not found",HttpStatus.NOT_FOUND);
        }
        checkRepository.save(check);
        return new ResponseEntity<>("Cheque Successfully created",HttpStatus.OK);
        
        
    }


    public void deleteChequeById(Long id){
        checkRepository.deleteById(id);
    }

    // Implement other service methods based on your application's requirements
}