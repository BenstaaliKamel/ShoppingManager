package com.cash_manager.api.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/test")
public class TestController {
  @Autowired
  @GetMapping("/anon")
  public String anonEndPoint() {
      return "everyone can see this";
  }

  @GetMapping("/users")
  @PreAuthorize("hasRole('USER')")
  public String usersEndPoint() {
    
    System.out.println("checking users");
    
    return "ONLY users can see this";
  }

  @GetMapping("/admins")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminsEndPoint() {
    System.out.println("checking admins");
    return "ONLY admins can see this";
  }
}