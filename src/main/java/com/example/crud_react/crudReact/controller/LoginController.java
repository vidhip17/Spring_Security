package com.example.crud_react.crudReact.controller;

import com.example.crud_react.crudReact.config.JwtTokenProvider;
import com.example.crud_react.crudReact.dto.LoginDto;
import com.example.crud_react.crudReact.dto.UserDto;
import com.example.crud_react.crudReact.entity.User;
import com.example.crud_react.crudReact.repository.UserRepo;
import com.example.crud_react.crudReact.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "")
public class LoginController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        if(userRepo.findByUserNameOrEmail(user.getUserName()) != null) {
            return new ResponseEntity<>("USERALREADYEXIST", HttpStatus.CONFLICT);
        };
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        user.setRoles(roles);

        userService.save(user);
        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    // User login (returns JWT token)
    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody UserDto loginRequest) {

        User user = userRepo.findByUserNameOrEmail(loginRequest.getUsername());

        if(user == null) {
            return new ResponseEntity<>(new LoginDto("USERNOTFOUND", null, null, null), HttpStatus.NOT_FOUND);
        } else {
            Boolean matche = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
            if(!matche) {
                return new ResponseEntity<>(new LoginDto("PASSWORDNOTMATCH", null, null, null), HttpStatus.BAD_REQUEST);
            }
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(loginRequest.getUsername());

        LoginDto loginDto = new LoginDto();
        loginDto.setToken(token);
        loginDto.setUsername(user.getUserName());
        loginDto.setRole(user.getRoles().toString());
        return ResponseEntity.ok(loginDto);
    }

    @PostMapping("/signout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization") != null ? request.getHeader("Authorization").replace("Bearer ", "") : null;

        if (token != null) {
            jwtTokenProvider.blacklistToken(token);
            return ResponseEntity.ok("Logged out successfully.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");

    }
}
