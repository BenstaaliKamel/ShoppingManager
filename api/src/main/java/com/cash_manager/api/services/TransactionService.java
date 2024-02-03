package com.cash_manager.api.services;

import com.cash_manager.api.Transaction;
import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransaction(Long id);
    Transaction getTransactionById(Long id);
    Transaction createTransaction(Transaction user);
    void deleteTransactionById(Long id);
    
    // Other service methods
}
