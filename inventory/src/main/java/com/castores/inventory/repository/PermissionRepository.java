package com.castores.inventory.repository;


import com.castores.inventory.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    // Método para encontrar un permiso por su nombre
    Permission findByName(String name);
}
