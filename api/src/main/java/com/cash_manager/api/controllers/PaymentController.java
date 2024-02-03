package com.cash_manager.api.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cash_manager.api.Payment;
import com.cash_manager.api.User;
import com.cash_manager.api.services.PaymentService;
import com.cash_manager.api.services.UserService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Payment>> getPaymentByUserId(@RequestHeader HttpHeaders headers){
        User user = userService.getUserFromHeader(headers);
        if(user !=null){
            return new ResponseEntity<>(paymentService.getPaymentByUserId(user.getId()),HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment,@RequestHeader HttpHeaders headers){
        payment.setUser(userService.getUserFromHeader(headers));
        return new ResponseEntity<>(paymentService.createPayment(payment),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deletePayment(@PathVariable long id,@RequestHeader HttpHeaders headers){
        Payment payment = paymentService.getPaymentById(id);
        if(payment==null){
            return new ResponseEntity<>("Card not found",HttpStatus.NOT_FOUND);
        }
        User user = userService.getUserFromHeader(headers);
        if(payment.getUser().getId()!=user.getId()){
            return new ResponseEntity<>("Trying to delete unpossessed card",HttpStatus.FORBIDDEN);
        }else{
            paymentService.deletePaymentById(id);
            return new ResponseEntity<>("Deletion successfull",HttpStatus.OK);
        }
    }

    
    
}
