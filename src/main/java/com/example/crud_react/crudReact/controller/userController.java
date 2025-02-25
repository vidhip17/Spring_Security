package com.example.crud_react.crudReact.controller;

import com.example.crud_react.crudReact.config.JwtTokenProvider;
import com.example.crud_react.crudReact.entity.User;
import com.example.crud_react.crudReact.repository.UserRepo;
import com.example.crud_react.crudReact.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user")
public class userController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    // Register new user
//    @PostMapping("/register")
//    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
//        User createdUser = userService.save(user);
//        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }
//
//    // User login (returns JWT token)
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody User loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtTokenProvider.generateToken(loginRequest.getUserName());
//        return ResponseEntity.ok(token);
//    }

    // Get all users (only for admin role)
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.update(id, userDetails);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
