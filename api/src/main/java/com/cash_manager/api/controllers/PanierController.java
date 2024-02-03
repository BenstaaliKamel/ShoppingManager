package com.cash_manager.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.cash_manager.api.Panier;
import com.cash_manager.api.User;
import com.cash_manager.api.services.PanierService;
import com.cash_manager.api.services.ProductsService;
import com.cash_manager.api.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/panier")
public class PanierController {

    @Autowired
    private PanierService panierService; // Assurez-vous d'avoir un service PanierService injecté
    @Autowired
    private UserService userService;
    @Autowired
    private ProductsService productsService;
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Panier>> obtenirTousLesPaniers(@RequestHeader HttpHeaders httpHeaders) {
        User user = userService.getUserFromHeader(httpHeaders);
        return new ResponseEntity<>(panierService.getAllPanier(user.getId()),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Panier> obtenirPanierParId(@PathVariable long id,@RequestHeader HttpHeaders httpHeaders) {
        User user = userService.getUserFromHeader(httpHeaders);
        Panier p = panierService.getPanierById(id);
        if(p!=null){
            if(p.getUser().getId()== user.getId()){
                return new ResponseEntity<>(p,HttpStatus.OK);
    
            }else{
                return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
            }

        }else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Panier> ajouterPanier(@RequestBody Panier panier,@RequestHeader HttpHeaders httpHeaders) {
        User user = userService.getUserFromHeader(httpHeaders);
        panier.setUser(user);
        List<Panier> p = panierService.getAllPanier(user.getId());
        long quantity = productsService.getProductsById(panier.getProducts().getId()).getQuantity();
        System.out.println(quantity);
        for(int i=0;i<p.size();i++){
            if(p.get(i).getProducts().getId()==panier.getProducts().getId()){
                
                if((p.get(i).getQuantity()+panier.getQuantity()>0&&p.get(i).getQuantity()+panier.getQuantity()<=quantity)){

                    p.get(i).setQuantity(p.get(i).getQuantity()+panier.getQuantity());
                    return new ResponseEntity<>(panierService.createPanier(p.get(i)),HttpStatus.OK);
                }else{
                    return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
                }
            }
        }
        if(panier.getQuantity()>quantity){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(panierService.createPanier(panier),HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> supprimerPanier(@PathVariable long id,@RequestHeader HttpHeaders httpHeaders) {
        User user = userService.getUserFromHeader(httpHeaders);
        Panier p = panierService.getPanierById(id);
        if(p==null){
            return new ResponseEntity<>("Panier not found",HttpStatus.NOT_FOUND);
        }else if(p.getUser().getId()!=user.getId()){
            return new ResponseEntity<>("Trying to delete unowned Panier",HttpStatus.FORBIDDEN);
        }
        panierService.deletePanierById(id);
        return new ResponseEntity<>("Panier Succesfully deleted",HttpStatus.OK);
    }
}