package com.castores.inventory.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.castores.inventory.controller.LoginController;
import com.castores.inventory.model.Role;
import com.castores.inventory.model.RolePermission;
import com.castores.inventory.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    // Logger para registrar eventos y errores en la aplicación
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    // Método para verificar si un rol tiene un permiso específico
    public boolean hasPermission(Role role, String permissionName) {

        logger.info("hasPermission------------------>{}", permissionName);
        logger.info("getPermissions------------------>{}", role.getPermissions());
        return role.getPermissions()
                .stream()
                .anyMatch(permission -> permission.getName().equals(permissionName));
    }

    // Método para verificar si un usuario tiene acceso a una acción específica
    public boolean hasAccess(Role role, String action) {

        logger.info("hasAccess role ------------------>{}", role);
        logger.info("hasAccess action ------------------>{}", action);
        // Lógica para mapear 'action' a 'permissionName' y luego verificar permiso
        String permissionName = mapActionToPermission(action);
        logger.info("hasAccess permissionName ------------------>{}", permissionName);
        return hasPermission(role, action);
    }

    // Método para mapear acciones a nombres de permisos (simulado)
    private String mapActionToPermission(String action) {
        // Implementación simulada, ajusta según tu estructura de permisos
        if (action.equalsIgnoreCase("create")) {
            return "CREATE_PERMISSION";
        } else if (action.equalsIgnoreCase("read")) {
            return "READ_PERMISSION";
        } else if (action.equalsIgnoreCase("update")) {
            return "UPDATE_PERMISSION";
        } else if (action.equalsIgnoreCase("delete")) {
            return "DELETE_PERMISSION";
        } else {
            return "";
        }
    }
}
