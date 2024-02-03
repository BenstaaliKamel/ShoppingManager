package com.cash_manager.api.services;

import com.cash_manager.api.Panier;
import com.cash_manager.api.repository.PanierRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PanierService  {

    private final PanierRepository panierRepository;

    public List<Panier> getAllPanier(Long id) {
        return panierRepository.getByUserId(id);
    }
    public Panier getPanierById(Long id) {
        return panierRepository.findById(id).orElse(null);
    }
    public Panier createPanier(Panier panier) {
        // Add any business logic or validation before saving the panier
        return panierRepository.save(panier);
    }
    public List<Panier> getPanierByUserId(Long id){
        List<Panier> p = panierRepository.getByUserId(id);
        return p;
    }
    
    // public Panier updatePanier(long id,Panier panier){
    //     Panier p = panierRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Panier id not found"));
    //     panier.set



    // }


    public void deletePanierById(Long id){
        panierRepository.deleteById(id);
    }
    public void deletePanierByUserId(Long user_id){
        panierRepository.deleteByUserId(user_id);
    }
    public void deletePanierByProductsId(Long user_id){
        panierRepository.deleteByUserId(user_id);
    }
    // Implement other service methods based on your application's requirements
}