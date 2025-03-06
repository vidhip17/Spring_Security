package com.example.crud_react.crudReact.controller;

import com.example.crud_react.crudReact.config.JwtTokenProvider;
import com.example.crud_react.crudReact.entity.Item;
import com.example.crud_react.crudReact.entity.User;
import com.example.crud_react.crudReact.repository.UserRepo;
import com.example.crud_react.crudReact.service.ItemService;
import com.example.crud_react.crudReact.serviceimpl.ItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping()
    public ResponseEntity<Item> createItem(@RequestBody Item item, HttpServletRequest request) {

        String token = jwtTokenProvider.getTokenFromRequest(request);
        String userName = jwtTokenProvider.extractUsername(token);
        User user = userRepo.findByUserNameOrEmail(userName);

        item.setCreatedBy(user);
        Item createdItem = itemService.save(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Integer id) {
        Optional<Item> item = itemService.findById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Integer id, @RequestBody Item itemDetails) {
        Item updatedItem = itemService.update(id, itemDetails);
        return updatedItem != null ? ResponseEntity.ok(updatedItem) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
