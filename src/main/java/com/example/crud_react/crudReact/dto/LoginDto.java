package com.example.crud_react.crudReact.dto;

import lombok.Data;

@Data
public class LoginDto {
    private Long userId;
    private String username;
    private String role;
    private String token;
    private String message;
    private String currentRole;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginDto() {
    }

    public LoginDto(Long id, String message, String username, String role, String token, String currentRole) {
        this.userId = id;
        this.username = username;
        this.role = role;
        this.token = token;
        this.message = message;
        this.currentRole = currentRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
