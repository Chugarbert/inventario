package com.castores.inventory.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.castores.inventory.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String string);
}