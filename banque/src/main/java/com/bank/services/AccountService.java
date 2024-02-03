package com.bank.services;

import com.bank.Account;
import com.bank.repository.AccountRepository;


import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService  {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }
    public Account getAccountById(String id) {
        return accountRepository.findByNumber(id).orElse(null);
    }
    public Account createAccount(Account account) {
        // Add any business logic or validation before saving the account
        return accountRepository.save(account);
    }
    public Account updateAccount(String id,Account account){
        Account p = accountRepository.findByNumber(id).orElseThrow(() -> new EntityNotFoundException("Product id not found"));
        p.setExpiration(account.getExpiration());
        p.setSecurityNumber(account.getSecurityNumber());
        p.setNumber(account.getNumber());
        p.setMoney(account.getMoney());
        return accountRepository.save(p);
    }
    public ResponseEntity<String> creditAccount(Account result,Account account,float value){
        if(result != null & result.getSecurityNumber()==account.getSecurityNumber() & result.getExpiration().getTime() == account.getExpiration().getTime()){
            if(result.getMoney()>= value&& value > 0){
                result.setMoney(result.getMoney()-value);
                result = updateAccount(result.getNumber(), result);
                System.out.println(result.getNumber()+" "+result.getMoney()+" "+value);
                return new ResponseEntity<>("Payment successfull",HttpStatus.OK);
            }else{
                System.out.println(result.getNumber()+" "+result.getMoney()+" "+value);
                return new ResponseEntity<>("Not enough money",HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Bad credentials",HttpStatus.FORBIDDEN);
        }
    }

    public void deleteAccountById(Long id){
        accountRepository.deleteById(id);
    }

    // Implement other service methods based on your application's requirements
}