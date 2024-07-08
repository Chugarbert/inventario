package com.castores.inventory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.castores.inventory.model.InventoryMovement;
import com.castores.inventory.model.Product;
import com.castores.inventory.model.Role;
import com.castores.inventory.model.User;
import com.castores.inventory.repository.InventoryMovementRepository;
import com.castores.inventory.repository.ProductRepository;
import com.castores.inventory.repository.RoleRepository;
import com.castores.inventory.repository.UserRepository;
import com.castores.inventory.service.AuthorizationService;
import com.castores.inventory.service.InventoryMovementService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class InventarioController {

    // Logger para registrar eventos y errores en la aplicación
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ProductRepository ProductRepository;

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private RoleRepository RoleRepository;

    @Autowired
    private InventoryMovementService inventoryMovementService;

    @Autowired
    private AuthorizationService authorizationService;

    /*
     * @GetMapping("/inventario")
     * public String inventario(Model model) {
     * List<Product> Products = ProductRepository.findAll();
     * logger.info("Lista de Productos {}", Products);
     * model.addAttribute("products", Products);
     * return "inventario";
     * }
     */
    @GetMapping("/inventario")
    public String inventario(Model model) {
        /*
         * List<Product> Products = ProductRepository.findAll();
         * logger.info("Lista de Productos {}", Products);
         * model.addAttribute("products", Products);
         */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String roleName = auth.getAuthorities().iterator().next().getAuthority(); // Obtener el rol del usuario
                                                                                  // autenticado
        Role role = RoleRepository.findByName(roleName);
        // Verificar si el usuario tiene acceso a la acción solicitada
        if (authorizationService.hasAccess(role, "Ver modulo inventario")) {
            logger.info("<------------ acceso concedido ---------->");
            return "inventario";
        } else {
            logger.info("<------------ acceso negado ---------->");
            return "acceso_negado";
        }

    }

    @GetMapping("/inventario_in")
    public String inventario_in(Model model) {
        // Obtener la autenticación actual del contexto de seguridad
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Verificar si el principal es del tipo UserDetails
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            logger.info("userDetails ---------->{}", userDetails.getUsername());
            User user = UserRepository.findByCorreo(userDetails.getUsername());

            logger.info("userData ------------->{}", user.getIdUsuario());
            List<Product> Products = ProductRepository.findAll();

            logger.info("Lista de Productos {}", Products);
            model.addAttribute("products", Products);
            model.addAttribute("userId", user.getIdUsuario());
        }

        return "inventario_in";
    }

    @GetMapping("/inventario_out")
    public String inventario_out(Model model) {
        // Obtener la autenticación actual del contexto de seguridad

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String roleName = auth.getAuthorities().iterator().next().getAuthority(); // Obtener el rol del usuario
                                                                                  // autenticado
        Role role = RoleRepository.findByName(roleName);
        // Verificar si el usuario tiene acceso a la acción solicitada
        if (authorizationService.hasAccess(role, "Ver modulo para salida de productos")) {
            logger.info("<------------ acceso concedido ---------->");
            if (auth != null && auth.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) auth.getPrincipal();
                logger.info("userDetails ---------->{}", userDetails.getUsername());
                User user = UserRepository.findByCorreo(userDetails.getUsername());

                logger.info("userData ------------->{}", user.getIdUsuario());
                List<Product> Products = ProductRepository.findByEstatus(1);

                logger.info("Lista de Productos {}", Products);
                model.addAttribute("products", Products);
                model.addAttribute("userId", user.getIdUsuario());
            }
        } else {
            logger.info("<------------ acceso negado ---------->");
            return "acceso_negado";
        }

        return "inventario_out";
    }

    @PostMapping("/inventario/agregar")
    public String agregarProduct(@RequestParam String nombre) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String roleName = auth.getAuthorities().iterator().next().getAuthority(); // Obtener el rol del usuario
                                                                                  // autenticado
        Role role = RoleRepository.findByName(roleName);
        // Verificar si el usuario tiene acceso a la acción solicitada
        if (authorizationService.hasAccess(role, "Agregar nuevos productos")) {
            logger.info("<------------ acceso concedido ---------->");
            Product Product = new Product();
            Product.setNombre(nombre);
            Product.setCantidad(0);
            Product.setEstatus(1); // Activo
            ProductRepository.save(Product);
            return "redirect:/inventario_in";
        } else {
            logger.info("<------------ acceso negado ---------->");
            return "acceso_negado";
        }

    }

    @PostMapping("/inventario/aumentar")
    public String aumentarInventario(@RequestParam Long id, @RequestParam int cantidad, @RequestParam Integer userId,
            Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String roleName = auth.getAuthorities().iterator().next().getAuthority(); // Obtener el rol del usuario
                                                                                  // autenticado
        Role role = RoleRepository.findByName(roleName);
        // Verificar si el usuario tiene acceso a la acción solicitada
        if (authorizationService.hasAccess(role, "Aumentar inventario")) {
            logger.info("<------------ acceso concedido ---------->");

            Product product = ProductRepository.findById(id).orElse(null);
            User user = UserRepository.findById(userId).orElse(null); // UserRepository.findById(userId).orElse(null);
            if (product == null) {
                model.addAttribute("error", "El producto con el ID especificado no existe.");
                List<Product> Products = ProductRepository.findAll();
                logger.info("Lista de Productos {}", Products);
                model.addAttribute("products", Products);
                model.addAttribute("userId", userId);
                return "inventario_in";
            }

            if (cantidad <= 0) {

                model.addAttribute("error", "La cantidad a aumentar debe ser mayor que cero.");
                List<Product> Products = ProductRepository.findAll();
                logger.info("Lista de Productos {}", Products);
                model.addAttribute("products", Products);
                model.addAttribute("userId", userId);
                return "inventario_in";
            }

            if (product.getEstatus() == 0) {
                model.addAttribute("error",
                        "No se puede modificar la existencia de un producto con estado inactivo. Por favor, vuelva a activar el producto antes de actualizar su existencia.");
                List<Product> Products = ProductRepository.findAll();
                logger.info("Lista de Productos {}", Products);
                model.addAttribute("products", Products);
                model.addAttribute("userId", userId);
                return "inventario_in";
            }

            product.setCantidad(product.getCantidad() + cantidad);
            ProductRepository.save(product);

            InventoryMovement movement = new InventoryMovement();
            movement.setProduct(product);
            movement.setType("ENTRADA");
            movement.setQuantity(cantidad);
            movement.setUser(user);
            movement.setDateTime(LocalDateTime.now());
            inventoryMovementService.saveMovement(movement);

            List<Product> products = ProductRepository.findAll();
            logger.info("Lista de Productos {}", products);
            model.addAttribute("products", products);
            model.addAttribute("userId", userId);

            return "inventario_in";
        } else {
            logger.info("<------------ acceso negado ---------->");
            return "acceso_negado";
        }

    }

    @PostMapping("/inventario/quitar")
    public String quiarInventario(@RequestParam Long id, @RequestParam int cantidad, @RequestParam Integer userId,
            Model model) {
        Product Product = ProductRepository.findById(id).orElse(null);
        User user = UserRepository.findById(userId).orElse(null);
        if (Product == null) {
            model.addAttribute("error", "El producto con el ID especificado no existe.");
            List<Product> Products = ProductRepository.findByEstatus(1);
            logger.info("Lista de Productos {}", Products);
            model.addAttribute("products", Products);
            model.addAttribute("userId", userId);
            return "inventario_out";
        } else {
            if (cantidad < 0 && Product.getCantidad() - cantidad < 0) {
                model.addAttribute("error", "No se puede disminuir la cantidad de inventario por debajo de 0.");
                List<Product> Products = ProductRepository.findByEstatus(1);
                logger.info("Lista de Productos {}", Products);
                model.addAttribute("products", Products);
                model.addAttribute("userId", userId);
                return "inventario_out";
            }
            Product.setCantidad(Product.getCantidad() - cantidad);
            ProductRepository.save(Product);

            InventoryMovement movement = new InventoryMovement();
            movement.setProduct(Product);
            movement.setType("SALIDA");
            movement.setQuantity(cantidad);
            movement.setUser(user);
            movement.setDateTime(LocalDateTime.now());
            inventoryMovementService.saveMovement(movement);

            List<Product> products = ProductRepository.findAll();
            logger.info("Lista de Productos {}", products);
            model.addAttribute("products", products);
        }

        List<Product> Products = ProductRepository.findByEstatus(1);
        logger.info("Lista de Productos {}", Products);
        model.addAttribute("products", Products);
        model.addAttribute("userId", userId);
        return "inventario_out";

    }

    @PostMapping("/inventario/estatus")
    public String cambiarEstatus(@RequestParam Long id, @RequestParam int estatus) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String roleName = auth.getAuthorities().iterator().next().getAuthority(); // Obtener el rol del usuario
                                                                                  // autenticado
        Role role = RoleRepository.findByName(roleName);
        // Verificar si el usuario tiene acceso a la acción solicitada
        if (authorizationService.hasAccess(role, "Dar de baja un producto")
                || authorizationService.hasAccess(role, "Reactivar un producto")) {
            logger.info("<------------ acceso concedido ---------->");

            Product Product = ProductRepository.findById(id).orElse(null);
            if (Product != null) {
                Product.setEstatus(estatus);
                ProductRepository.save(Product);
            }

            return "redirect:/inventario_in";
        } else {
            logger.info("<------------ acceso negado ---------->");
            return "acceso_negado";
        }

    }

}
