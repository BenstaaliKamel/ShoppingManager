package com.bank.services;

import com.bank.Pending;
import com.bank.repository.AccountRepository;
import com.bank.repository.PendingRepository;


import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PendingService  {

    private final PendingRepository pendingRepository;
    public PendingService(PendingRepository pendingRepository,AccountRepository accountRepository){
        this.pendingRepository = pendingRepository;
    }

    public List<Pending> getAllPending() {
        return pendingRepository.findAll();
    }
    public Pending getPendingById(Long id) {
        return pendingRepository.findById(id).orElse(null);
    }
    public Pending createPending(Pending pending) {
        return pendingRepository.save(pending);
    }
    public Pending updatePending(long id,boolean isConfirmed, boolean isRefused) {
        Pending p = getPendingById(id);
        p.setConfirmed(isConfirmed);
        p.setRefused(isRefused);
        return pendingRepository.save(p);
    }


    public void deletePendingById(Long id){
        pendingRepository.deleteById(id);
    }

    // Implement other service methods based on your application's requirements
}