package com.example.crud_react.crudReact.service;

import com.example.crud_react.crudReact.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    User update(Long id, User user);

    void delete(Long id);

    User findByUsername(String username);
}
