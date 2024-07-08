package com.castores.inventory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.castores.inventory.model.InventoryMovement;
import com.castores.inventory.model.Product;
import com.castores.inventory.model.Role;
import com.castores.inventory.repository.InventoryMovementRepository;
import com.castores.inventory.repository.ProductRepository;
import com.castores.inventory.repository.RoleRepository;
import com.castores.inventory.service.AuthorizationService;
import com.castores.inventory.service.InventoryMovementService;
import java.util.List;

@Controller
public class InventoryMovementController {

    // Logger para registrar eventos y errores en la aplicación
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private InventoryMovementService inventoryMovementService;

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private RoleRepository RoleRepository;

    /*
     * @GetMapping
     * public String getAllMovements(Model model) {
     * List<InventoryMovement> movements =
     * inventoryMovementService.getAllMovements();
     * model.addAttribute("movements", movements);
     * return "movements";
     * }
     * 
     * @GetMapping("/type")
     * public String getMovementsByType(@RequestParam String type, Model model) {
     * List<InventoryMovement> movements =
     * inventoryMovementService.getMovementsByType(type);
     * model.addAttribute("movements", movements);
     * return "movements";
     * }
     */

    @GetMapping("/historial")
    public String historial(Model model) {
        // Obtener todos los movimientos de inventario desde el repositorio
        logger.info("historial ---------->");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String roleName = auth.getAuthorities().iterator().next().getAuthority(); // Obtener el rol del usuario
                                                                                  // autenticado
        Role role = RoleRepository.findByName(roleName);
        // Verificar si el usuario tiene acceso a la acción solicitada
        if (authorizationService.hasAccess(role, "Ver modulo del historico")) {
            logger.info("<------------ acceso concedido ---------->");
            List<InventoryMovement> movements = inventoryMovementRepository.findAll();

            // Agregar la lista de movimientos al modelo para la vista Thymeleaf
            model.addAttribute("movements", movements);
            logger.info("movements ----------> {}", movements);
            return "historial";
        } else {
            logger.info("<------------ acceso negado ---------->");
            return "acceso_negado";
        }

    }
}
