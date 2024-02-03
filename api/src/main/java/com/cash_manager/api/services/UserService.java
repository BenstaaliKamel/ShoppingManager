package com.cash_manager.api.services;

import com.cash_manager.api.User;
import com.cash_manager.api.repository.UserRepository;
import com.cash_manager.api.security.JwtService;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    public UserDetailsService userDetailsService(){
        return new UserDetailsService(){
            @Override
            public UserDetails loadUserByUsername(String username){
                return userRepository.findByEmail(username).orElseThrow(() ->new UsernameNotFoundException("Username not found"));
            }

            
        };
    }
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public User createUser(User user) {
        // Add any business logic or validation before saving the user
        return userRepository.save(user);
    }
    public User updateUser(long id,User user){
        User u =userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
        u.setEmail(user.getEmail());
        u.setNom(user.getNom());
        u.setPassword(user.getPassword());
        return userRepository.save(u);
    
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }   
    

    public User getUserFromHeader(HttpHeaders headers){
        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        return getUserByToken(token);
    }
    public User getUserByToken(String token){
        String email = getUserEmail(token);
        Optional<User> user = userRepository.findByEmail(email);
        if(user != null){
            return user.get();
        }else{
            return null;
        }

    }
    public String getUserEmail(String token){
        return Jwts
        .parserBuilder()
        .setSigningKey(jwtService.getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    }
    // Implement other service methods based on your application's requirements
}