package com.cash_manager.api.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javatuples.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cash_manager.api.User;
import com.cash_manager.api.dto.JwtAuthenticationResponse;
import com.cash_manager.api.dto.SignInRequest;
import com.cash_manager.api.dto.SignUpRequest;
import com.cash_manager.api.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    
    @PostMapping("/signup")
    public ResponseEntity<Pair<User,String>> signup(@RequestBody SignUpRequest request) {
        Pair<User,JwtAuthenticationResponse> auth= authenticationService.signup(request);
        
        if(auth==null){
            return new ResponseEntity<>(Pair.with(new User(), "Invalid email or password"),HttpStatus.BAD_REQUEST);


        }else{
            return new ResponseEntity<>(Pair.with(auth.getValue0(),auth.getValue1().getToken() ),HttpStatus.OK);

        }
    }

    @PostMapping("/signin")
    public ResponseEntity<Pair<User,String>>  signin(@RequestBody SignInRequest request) {
        Pair<User,JwtAuthenticationResponse> auth= authenticationService.signin(request);
        
        if(auth==null){
            return new ResponseEntity<>(Pair.with(null, "Invalid email or password"),HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(Pair.with(auth.getValue0(),auth.getValue1().getToken()),HttpStatus.OK);
        }
        
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public void isAuthenticated(){
    }
    
}