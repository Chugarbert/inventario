package com.castores.inventory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.castores.inventory.controller.LoginController;

@Entity
@Table(name = "Users")
public class User {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private int idUsuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo")
    //private String correo;
    private String correo;

    @Column(name = "password")
    private String password;

    @Column(name = "estatus")
    private Integer estatus;

    @ManyToOne
    @JoinColumn(name = "idRol")
    private Role role;

    // Getters and setters, constructors, etc.
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
/*     public int getId() {
        return idUsuario;
    } */

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
/* 
    public String getUsername() {
        logger.info("username: {}", correo);
        return correo;
    }

    public void setUsername(String correo) {
        this.correo = correo;
    }  */

    public String getPassword() {
        logger.info("password: {}", password);
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Role getRole() {
        logger.info("role: {}", role);
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
