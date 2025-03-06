package com.example.crud_react.crudReact.serviceimpl;

import com.example.crud_react.crudReact.entity.User;
import com.example.crud_react.crudReact.repository.UserRepo;
import com.example.crud_react.crudReact.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<User> findAll() {
        logger.info("find all users");
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        logger.info("find user by id");
        return userRepo.findById(id);
    }

    @Override
    public User save(User user) {
        logger.info("save user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User update(Long id, User userDetail) {
        logger.info("update user");
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUserName(userDetail.getUserName());
            user.setEmail(userDetail.getEmail());
            user.setPassword(passwordEncoder.encode(userDetail.getPassword()));  // Encrypt new password
            user.setRoles(userDetail.getRoles());
            return userRepo.save(user);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        logger.info("delete user by id");
        userRepo.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        logger.info("findBy username");
        return userRepo.findByUserName(username);
    }

    @Override
    public User updateCurrentRole(Long id, String role) {
        logger.info("update current role");
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCurrentRole(role);
            return userRepo.save(user);
        }
        return null;
    }
}
