package com.example.crud_react.crudReact.repository;


import com.example.crud_react.crudReact.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUserName(String username);

    @Query(value = "select * from user_master where email = :username OR user_name = :username", nativeQuery = true)
    User findByUserNameOrEmail(String username);
}
