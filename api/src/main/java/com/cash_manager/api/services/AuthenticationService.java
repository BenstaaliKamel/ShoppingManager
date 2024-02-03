package com.cash_manager.api.services;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cash_manager.api.User;
import com.cash_manager.api.dto.JwtAuthenticationResponse;
import com.cash_manager.api.dto.SignInRequest;
import com.cash_manager.api.dto.SignUpRequest;
import com.cash_manager.api.repository.UserRepository;
import com.cash_manager.api.security.JwtService;



@Service
public class AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
    @Autowired
    public AuthenticationService (UserRepository userRepository,UserService userService,PasswordEncoder passwordEncoder,JwtService jwtService,AuthenticationManager authenticationManager){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
        this.authenticationManager=authenticationManager;
        

    }
  public Pair<User,JwtAuthenticationResponse> signup(SignUpRequest request) {
      var user = User
                  .builder()
                  .nom(request.getName())
                  .email(request.getEmail())
                  .password(passwordEncoder.encode(request.getPassword()))
                  .admin(false)
                  .build();
      try{
        user = userRepository.save(user);

      }catch(Exception e){
        return Pair.with(null,null);
      }
      var jwt = jwtService.generateToken(user);
      User u = new User();
      u.setNom(user.getNom());
      u.setEmail(user.getEmail());
      u.setAdmin(user.getAdmin());
      return Pair.with(u,JwtAuthenticationResponse.builder().token(jwt).build());
  }


  public Pair<User,JwtAuthenticationResponse> signin(SignInRequest request) {   
    try{
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    }catch(Exception e){
      return null;
    }
      User user = userRepository.findByEmail(request.getEmail())
              .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
      String jwt = jwtService.generateToken(user);
      User u = new User();
      u.setNom(user.getNom());
      u.setEmail(user.getEmail());
      u.setAdmin(user.getAdmin());
      return Pair.with(u, JwtAuthenticationResponse.builder().token(jwt).build());
  }

  public void signout(SignInRequest request){

  }
  
}