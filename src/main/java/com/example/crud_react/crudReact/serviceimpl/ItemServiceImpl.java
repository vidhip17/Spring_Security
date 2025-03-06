package com.example.crud_react.crudReact.serviceimpl;

import com.example.crud_react.crudReact.entity.Item;
import com.example.crud_react.crudReact.repository.ItemRepo;
import com.example.crud_react.crudReact.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private static Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepo itemRepo;

    @Override
    public List<Item> findAll() {
        logger.info("itemservice findAll() called");
        return itemRepo.findAll();
    }

//    @Cacheable(value = "items", key = "#id")
    @Override
    public Optional<Item> findById(Integer id) {
        logger.info("itemservice findById() called");
        return itemRepo.findById(id);
    }

//    @CacheEvict(value = "items", key = "#item.id")
    @Override
    public Item save(Item item) {
        logger.info("itemservice save() called");
        return itemRepo.save(item);
    }

//    @CachePut(value = "items", key = "#item.id")
    @Override
    public Item update(Integer id, Item item) {
        logger.info("itemservice update() called");
        Item itemFound = itemRepo.findById(id).orElse(null);
        if(itemFound != null) {
            itemFound.setName(item.getName());
            itemFound.setPrice(item.getPrice());
            itemFound.setDescription(item.getDescription());
            return itemRepo.save(itemFound);
        }
        return null;
    }

    @CacheEvict(value = "items", key = "#id")
    @Override
    public void delete(Integer id) {
        logger.info("itemservice delete() called");
        itemRepo.deleteById(id);
    }

}
