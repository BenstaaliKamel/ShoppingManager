package com.cash_manager.api.controllers;

import com.cash_manager.api.services.UserService;
import com.cash_manager.api.User;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return this.userService.getAllUser();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserById(@PathVariable long id){
        return this.userService.getUserById(id);

    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public User addUser(@RequestBody User User) {
        
        return userService.createUser(User);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateUserById(@PathVariable long id,@RequestBody User user){
        return userService.updateUser(id, user);
    }
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable long id){
        this.userService.deleteUserById(id);
    }

    // Implement other CRUD operations as needed (GET by ID, PUT, DELETE)
}