package com.castores.inventory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilController {

    // Logger para registrar eventos y errores en la aplicación
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/perfil")
    public String perfil(Model model) {
        // Obtener la autenticación actual del contexto de seguridad
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Verificar si el usuario está autenticado
        if (auth != null && auth.isAuthenticated()
                && auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            // Obtener el usuario autenticado desde el contexto de seguridad
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth
                    .getPrincipal();
            
            // Pasar los datos del usuario como atributos al modelo
            model.addAttribute("nombre", user.getUsername()); // Usar getUsername() o algún otro método que proporcione
                                                              // el nombre de usuario
            model.addAttribute("correo", user.getUsername() + "@example.com"); // Ejemplo de correo
            model.addAttribute("rol", "ROLE_USER"); // Ejemplo de rol, deberías obtenerlo de las autorizaciones de
                                                    // Spring Security

            // Renderizar la vista de perfil
            return "perfil"; // Se espera que "perfil" sea el nombre de la vista Thymeleaf
        } else {
            // Manejar el caso donde el usuario no está autenticado
            return "redirect:/login";
        }
    }
}
