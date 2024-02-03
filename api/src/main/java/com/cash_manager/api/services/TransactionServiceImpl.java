package com.cash_manager.api.services;

import com.cash_manager.api.Transaction;
import com.cash_manager.api.repository.TransactionRepository;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getAllTransaction(Long id) {
        return transactionRepository.getByUserId(id);
    }

    @Override
    public Transaction getTransactionById(Long id) {
        Transaction t = transactionRepository.findById(id).orElse(null);
        if(t!=null){
                return t;
        }else{

            return null; 
        }
    }

    @Override
    public Transaction createTransaction(Transaction user) {
        // Add any business logic or validation before saving the user
        return transactionRepository.save(user);
    }
    @Override
    public void deleteTransactionById(Long id){
        transactionRepository.deleteById(id);
    }   
    // Implement other service methods based on your application's requirements
}