// UserDetailsServiceImpl.java
package com.castores.inventory.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.castores.inventory.controller.LoginController;
import com.castores.inventory.model.Role;
import com.castores.inventory.model.User;
import com.castores.inventory.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    // Logger para registrar eventos y errores en la aplicación
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    // Inyección de dependencias: UserRepository para acceder a la base de datos de usuarios
    @Autowired
    private UserRepository userRepository;

    // Método para cargar los detalles del usuario por correo electrónico
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        logger.info("En UserDetails loadUserByUsername ------------------------------------------------->" );
        logger.info("Intento de login para el correo: {}", correo);
        // Buscar el usuario en la base de datos por correo electrónico
        User user = userRepository.findByCorreo(correo);

        if (user == null) {
            // Si no se encuentra el usuario, lanzar una excepción
            logger.info("Usuario no encontrado");
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Crear un objeto UserDetails con los detalles del usuario
        return new org.springframework.security.core.userdetails.User(
            user.getCorreo(),
            user.getPassword(),
            getAuthorities(user.getRole())
        );
    }

    // Método para obtener los roles y permisos del usuario
    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        // Implementar lógica para obtener roles y permisos del usuario
        // Aquí puedes usar Spring Security para definir roles y permisos
        // Ejemplo:
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }
}