package com.castores.inventory.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.castores.inventory.model.Permission;
import com.castores.inventory.model.Role;
import com.castores.inventory.model.RolePermission;
import com.castores.inventory.model.User;
import com.castores.inventory.repository.PermissionRepository;
import com.castores.inventory.repository.RolePermissionRepository;
import com.castores.inventory.repository.RoleRepository;
import com.castores.inventory.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Bean
    CommandLineRunner initRolesAndUsers() {
        return args -> {
            // Create permissions if they do not exist
            String[] permissionNames = {
                    "Iniciar session",
                    "Ver modulo inventario",
                    "Agregar nuevos productos",
                    "Aumentar inventario",
                    "Dar de baja un producto",
                    "Reactivar un producto",
                    "Ver modulo salida de productos",
                    "Sacar inventario del almacen",
                    "Ver modulo del historico"
            };

            Set<Permission> permissions = new HashSet<>();
            for (String name : permissionNames) {
                Permission permission = permissionRepository.findByName(name);
                if (permission == null) {
                    permission = new Permission();
                    permission.setName(name);
                    permissionRepository.save(permission);
                }
                permissions.add(permission);
            }
            // Crear roles si no existen
            Role adminRole = roleRepository.findByName("Administrador");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("Administrador");
                roleRepository.save(adminRole);
            }

            Role almacenistaRole = roleRepository.findByName("Almacenista");
            if (almacenistaRole == null) {
                almacenistaRole = new Role();
                almacenistaRole.setName("Almacenista");
                roleRepository.save(almacenistaRole);
            }

            Role guyRole = roleRepository.findByName("guyRole");
            if (guyRole == null) {
                guyRole = new Role();
                guyRole.setName("guyRole");
                roleRepository.save(guyRole);
            }
/* 
            "Iniciar session",
            "Ver modulo inventario",
            "Agregar nuevos productos",
            "Aumentar inventario",
            "Dar de baja un producto",
            "Reactivar un producto",
            "Ver modulo salida de productos",
            "Sacar inventario del almacen",
            "Ver modulo del historico"
             */
            // Assign permissions to roles
            Set<String> adminPermissionNames = Set.of(
                    "Iniciar session",
                    "Ver modulo del historico",
                    "Ver modulo inventario",
                    "Agregar nuevos productos",
                    "Dar de baja un producto",
                    "Reactivar un producto",
                    "Aumentar inventario");

            Set<String> almacenistaPermissionNames = Set.of(
                    "Iniciar session",
                    "Ver modulo inventario",
                    "Ver modulo para salida de productos",
                    "Sacar inventario del almacen");

            assignPermissionsToRole(adminRole, adminPermissionNames, permissions);
            assignPermissionsToRole(almacenistaRole, almacenistaPermissionNames, permissions);
            // Crear usuarios si no existen
            if (userRepository.findByCorreo("admin@example.com") == null) {
                User adminUser = new User();
                adminUser.setNombre("Admin");
                adminUser.setCorreo("admin@example.com");
                adminUser.setPassword(passwordEncoder.encode("adminpassword"));
                adminUser.setEstatus(1);
                adminUser.setRole(adminRole);
                userRepository.save(adminUser);
            }

            if (userRepository.findByCorreo("user1@example.com") == null) {
                User user1 = new User();
                user1.setNombre("User1");
                user1.setCorreo("user1@example.com");
                user1.setPassword(passwordEncoder.encode("user1password"));
                user1.setEstatus(1);
                user1.setRole(almacenistaRole);
                userRepository.save(user1);
            }

            if (userRepository.findByCorreo("user2@example.com") == null) {
                User user1 = new User();
                user1.setNombre("User2");
                user1.setCorreo("user2@example.com");
                user1.setPassword(passwordEncoder.encode("user2password"));
                user1.setEstatus(1);
                user1.setRole(guyRole);
                userRepository.save(user1);
            }

        };
    }

        private void assignPermissionsToRole(Role role, Set<String> permissionNames, Set<Permission> allPermissions) {
        Set<Permission> rolePermissions = new HashSet<>();
        for (Permission permission : allPermissions) {
            if (permissionNames.contains(permission.getName())) {
                RolePermission rolePermission = new RolePermission(role, permission);
                rolePermissionRepository.save(rolePermission);
                rolePermissions.add(permission);
            }
        }
        role.setPermissions(rolePermissions);
        roleRepository.save(role);
    }
}
