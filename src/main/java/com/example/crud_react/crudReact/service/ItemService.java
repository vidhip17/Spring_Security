package com.example.crud_react.crudReact.service;

import com.example.crud_react.crudReact.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ItemService {

    List<Item> findAll();

    Optional<Item> findById(Integer id);

    Item save(Item item);

    Item update(Integer id, Item item);

    void delete(Integer id);
}
