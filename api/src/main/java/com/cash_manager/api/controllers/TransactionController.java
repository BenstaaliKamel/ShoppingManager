package com.cash_manager.api.controllers;

import com.cash_manager.api.services.BankService;
import com.cash_manager.api.services.PanierService;
import com.cash_manager.api.services.PaymentService;
import com.cash_manager.api.services.ProductsService;
import com.cash_manager.api.services.TransactionService;
import com.cash_manager.api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cash_manager.api.Cheque;
import com.cash_manager.api.Panier;
import com.cash_manager.api.Payment;
import com.cash_manager.api.Transaction;
import com.cash_manager.api.User;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
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

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final UserService userService;
    private final TransactionService transactionService;
    private final ProductsService productsService;
    private final PanierService panierService;
    private final PaymentService paymentService;
    BankService bankService;

    @Autowired
    public TransactionController(TransactionService transactionService,PanierService panierService,BankService bankService,ProductsService productsService,UserService userService,PaymentService paymentService) {
        this.transactionService = transactionService;
        this.productsService = productsService;
        this.panierService=panierService;
        this.bankService=bankService;
        this.userService=userService;
        this.paymentService=paymentService;
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransaction(@RequestHeader HttpHeaders httpHeaders) {
        User user = userService.getUserFromHeader(httpHeaders);
        return new ResponseEntity<>(transactionService.getAllTransaction(user.getId()),HttpStatus.OK);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable long id,@RequestHeader HttpHeaders httpHeaders){
        User user = userService.getUserFromHeader(httpHeaders);
        System.out.println(user);
        Transaction transaction = transactionService.getTransactionById(id);
        if(transaction!=null){

            if(transaction.getUser().getId()!=user.getId()){
                return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
            }else{
                return new ResponseEntity<>(transaction,HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>(transaction,HttpStatus.NOT_FOUND);
        }
        }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}")
    public ResponseEntity<String> addTransaction(@RequestHeader HttpHeaders httpHeaders,@PathVariable long id) {
        User user = userService.getUserFromHeader(httpHeaders);
        List<Panier> p = panierService.getPanierByUserId(user.getId());
        List<Payment> payments = paymentService.getPaymentByUserId(user.getId());
        boolean test = false;
        for (Payment payment2 : payments) {
            if(payment2.isPending()){
                if(payment2.getPendingId()==id){
                    test = true;
                    break;
                }else{
                    break;
                }
            }
        }
        if(test ){
            return new ResponseEntity<>("Payment already pending",HttpStatus.FORBIDDEN); 
        }
        if(p==null || p.size()==0){
            //code nothing to pay ???
            return new ResponseEntity<>("Noting to pay for",HttpStatus.BAD_REQUEST);
        }
        float sum=0;
        
        for (int i=0;i<p.size();i++) {
            Panier panier = p.get(i);
            if(panier.getUser().getId()!=user.getId()){
                //403
                return new ResponseEntity<>("Trying to buy another users items",HttpStatus.FORBIDDEN);
            }
            sum+=panier.getProducts().getPrice()*panier.getQuantity();
            
            if(productsService.getProductsById(panier.getProducts().getId()).getQuantity() < panier.getProducts().getQuantity()){
                //can't buy more than there is
                return new ResponseEntity<>("Trying to buy more than there is",HttpStatus.BAD_REQUEST);
            }
        }
        
        try{
            Payment payment = paymentService.getPaymentById(id);
            if(payment==null){
                return new ResponseEntity<>("Card not found",HttpStatus.NOT_FOUND);
            }
            if(payment.getUser().getId()!=user.getId() || payment.isPending()){
                return new ResponseEntity<>("Forbidden action",HttpStatus.FORBIDDEN);
            }
            ObjectMapper objectMapper= new ObjectMapper();
            ClientResponse response = bankService.makePostRequest("http://bank:4001/api/account/pay/"+sum,objectMapper.writeValueAsString(payment));
            if(response.statusCode()==HttpStatus.OK){
                String data= response.bodyToMono(String.class).block();
                paymentService.updatePayment(true, Integer.parseInt(data), id);
                return new ResponseEntity<>(data,HttpStatus.OK);
            }else{
                return new ResponseEntity<>("ERROR ON BANK PAYEMENT",response.statusCode());
            }
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Internal server error "+e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/procedure/{id}")
    public ResponseEntity<String> isPaymentDone(@RequestHeader HttpHeaders httpHeaders,@PathVariable long id){
        User user = userService.getUserFromHeader(httpHeaders);
        Payment rightCreditCard=null;
        List<Payment> payments = paymentService.getPaymentByUserId(user.getId());
        boolean test = false;
        for (Payment payment2 : payments) {
            if(payment2.isPending()){
                if(payment2.getPendingId()==id){
                    test = true;
                    rightCreditCard=payment2;
                    break;
                }else{
                    break;
                }
            }
        }
        if(!test ){
            return new ResponseEntity<>("Acquiring another person's payment",HttpStatus.FORBIDDEN); 
        }
        ClientResponse response = bankService.makeGetRequest("http://bank:4001/api/pending/"+id);
        
        if(response.statusCode()!=HttpStatus.OK){
            return new ResponseEntity<>(null,response.statusCode());
        }
        
        Transaction transaction = new Transaction();
        List<Panier> p = panierService.getPanierByUserId(user.getId());
        if(p==null || p.size()==0){
            return new ResponseEntity<>("No items found",HttpStatus.BAD_REQUEST);
        }
        transaction.setProductId(new Long[p.size()]);
        transaction.setProductQuantity(new int[p.size()]);
        transaction.setProductPrices(new float[p.size()]);
        float sum=0;
        for (int i=0;i<p.size();i++) {
            Panier panier = p.get(i);
            transaction.getProductId()[i]=panier.getProducts().getId();
            transaction.getProductQuantity()[i]=panier.getQuantity();
            transaction.getProductPrices()[i]=panier.getProducts().getPrice();
            sum+=transaction.getProductPrices()[i]*transaction.getProductQuantity()[i];
            if(productsService.getProductsById(panier.getProducts().getId()).getQuantity() < panier.getProducts().getQuantity()){
                //can't buy more than there is
                return new ResponseEntity<>("There isn't enough items in the inventory",HttpStatus.BAD_REQUEST);
            }
        }
        transaction.setTotalPrices(sum);
        for( int i=0;i<p.size();i++){
            productsService.reduceProducts(transaction.getProductId()[i],transaction.getProductQuantity()[i]);
        }
        paymentService.updatePayment(false, 0, rightCreditCard.getId());
        panierService.deletePanierByUserId(user.getId());
        transaction.setUser(user);
        transactionService.createTransaction(transaction);
        return new ResponseEntity<>("Items bough",HttpStatus.OK);
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cheque")
    public ResponseEntity<Transaction> addChequeTransaction(@RequestHeader HttpHeaders httpHeaders,@RequestBody Cheque cheque ) {
        User user = userService.getUserFromHeader(httpHeaders);
        Transaction transaction = new Transaction();
        List<Panier> p = panierService.getPanierByUserId(user.getId());
        if(p==null || p.size()==0 || cheque == null || cheque.getId()==0 || cheque.getAccount().getNumber()==""){
            //code nothing to pay ???
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        float sum=0;
        transaction.setProductId(new Long[p.size()]);
        transaction.setProductQuantity(new int[p.size()]);
        transaction.setProductPrices(new float[p.size()]);
        for (int i=0;i<p.size();i++) {
            Panier panier = p.get(i);
            if(panier.getUser().getId()!=user.getId()){
                //403
                return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
            }
            sum+=panier.getProducts().getPrice()*panier.getQuantity();
            transaction.getProductId()[i]=panier.getProducts().getId();
            transaction.getProductQuantity()[i]=panier.getQuantity();
            transaction.getProductPrices()[i]=panier.getProducts().getPrice();
            if(productsService.getProductsById(transaction.getProductId()[i]).getQuantity() < transaction.getProductQuantity()[i]){
                //can't buy more than there is
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
        }
        transaction.setTotalPrices(sum);
        transaction.setUser(user);
        
        try{
            ObjectMapper objectMapper= new ObjectMapper();
            ClientResponse response = bankService.makePostRequest("http://bank:4001/api/cheque/value/",objectMapper.writeValueAsString(cheque));
            if(response.statusCode()==HttpStatus.OK){
                String data =response.bodyToMono(String.class).block();
                System.out.println(data);
                int value = Integer.parseInt(data);
                System.out.println(value+" it works ");
                System.out.println(sum);
                if(value >= sum){
                    response = bankService.makePostRequest("http://bank:4001/api/cheque/"+cheque.getId(),objectMapper.writeValueAsString(null));
                    if(response.statusCode()==HttpStatus.OK){
                        for(int i=0;i<p.size();i++){
                            productsService.reduceProducts(transaction.getProductId()[i],transaction.getProductQuantity()[i]);
                        }
                        panierService.deletePanierByUserId(user.getId());
                        Transaction t=transactionService.createTransaction(transaction);
                        return new ResponseEntity<>(t,HttpStatus.OK);
                    }else{
                        return new ResponseEntity<>(null,response.statusCode());

                    }

                }else{
                    return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);

                }
                
            }
            return new ResponseEntity<>(null,response.statusCode());
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable long id){
        Transaction t = transactionService.getTransactionById(id);
        if(t==null){
            return new ResponseEntity<>("Item not found",HttpStatus.NOT_FOUND);
        }
        transactionService.deleteTransactionById(id);
        return new ResponseEntity<String>("message",HttpStatus.OK);
        
    }

}