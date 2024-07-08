package com.castores.inventory.repository;


import com.castores.inventory.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    // Métodos adicionales según sea necesario para operaciones relacionadas con los roles y permisos
}
