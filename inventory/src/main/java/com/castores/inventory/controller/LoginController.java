package com.castores.inventory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.castores.inventory.model.Role;
import com.castores.inventory.repository.RoleRepository;
import com.castores.inventory.repository.UserRepository;
import com.castores.inventory.service.AuthorizationService;

@Controller
public class LoginController {

    // Logger para registrar eventos y errores en la aplicación
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    // Inyección de dependencias: AuthenticationManager para autenticar usuarios
    @Autowired
    private AuthenticationManager authenticationManager;

    // Inyección de dependencias: UserRepository para acceder a la base de datos de
    // usuarios
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository RoleRepository;

        @Autowired
    private AuthorizationService authorizationService;

    // Método para mostrar la página de login
    @GetMapping("/login")
    public String loginPage() {
        logger.info("Accediendo a la pagina de login");
        return "login"; // Nombre de tu archivo de vista de inicio de sesión
    }

    // Método para procesar el formulario de login
    @PostMapping("/login")
    public String login(@RequestParam("correo") String correo,
            @RequestParam("password") String password,
            Model model) {
        logger.info("Post login: {}", correo);


        try {
            // Crear un token de autenticación con el correo y contraseña proporcionados
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(correo, password);

            // Autenticar el usuario utilizando el AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(authToken);

            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Redirigir según el rol del usuario
            if (authentication != null && authentication.isAuthenticated()) {
                logger.info("Autenticación exitosa para el usuario: {}", correo);
                String roleName = authentication.getAuthorities().iterator().next().getAuthority(); // Obtener el rol del usuario autenticado
                Role role = RoleRepository.findByName(roleName);
                // Verificar si el usuario tiene acceso a la acción solicitada
                if (authorizationService.hasAccess(role, "Ver modulo inventario")) {
                    logger.info("<------------ acceso concedido ---------->");
                    return "redirect:/home"; // Redirigir a la página de inicio
                } else {
                    logger.info("<------------ acceso negado ---------->");
                    return "acceso_negado";
                }
                
            } else {
                model.addAttribute("error", "Error de autenticación");
                return "login"; // Retornar a la página de login con mensaje de error si falla la autenticación
            }

        } catch (AuthenticationException e) {
            logger.info("Error de autenticación para el usuario: {}", correo, e);
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            // return "login"; // Retorna a la página de login con un mensaje de error
            return "redirect:/login?error"; // Redirige de nuevo al login con un mensaje de error
        }
    }
}