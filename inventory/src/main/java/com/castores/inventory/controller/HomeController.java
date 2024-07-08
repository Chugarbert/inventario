package com.castores.inventory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller
public class HomeController {

      // Logger para registrar eventos y errores en la aplicación
      private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

/*     @GetMapping("/home")
    public String homePage() {
        return "home"; // Nombre de la vista de la página de inicio
    } */

    @GetMapping(value = "/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority(); // Obtener el rol del usuario autenticado
        logger.info("role {}", role);
        model.addAttribute("userRole", role); // Pasar el rol como atributo al modelo
        return "home";
    }
}