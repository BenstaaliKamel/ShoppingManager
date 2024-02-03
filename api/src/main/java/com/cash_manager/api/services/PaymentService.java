package com.cash_manager.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cash_manager.api.Payment;
import com.cash_manager.api.repository.PaymentRepository;

@Service
public class PaymentService {
    @Autowired
    public PaymentRepository paymentRepository;

    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }
    public Payment createPayment(Payment panier) {
        // Add any business logic or validation before saving the panier
        return paymentRepository.save(panier);
    }
    public Payment updatePayment(boolean pending,long value,long id){
        Payment old = paymentRepository.findById(id).orElse(null);
        old.setPending(pending);
        old.setPendingId(value);
        return paymentRepository.save(old);
    }
    public List<Payment> getPaymentByUserId(Long id){
        return paymentRepository.getByUserId(id);
    }
    public void deletePaymentById(Long id){
        paymentRepository.deleteById(id);
    }
    
}
