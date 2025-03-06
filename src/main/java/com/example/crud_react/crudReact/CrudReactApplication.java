package com.example.crud_react.crudReact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.crud_react.crudReact.entity")
@EnableJpaRepositories("com.example.crud_react.crudReact.repository")
@EnableCaching
public class CrudReactApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudReactApplication.class, args);
	}

}
