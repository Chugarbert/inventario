package com.castores.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.castores.inventory.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByCorreo(String correo);

    //User findByUsername(String nombre);

   // User findByUsername(String username);
}