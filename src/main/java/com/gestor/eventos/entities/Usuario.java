package com.gestor.eventos.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

@Entity
@Table(name = "USUARIOS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMBRE_USUARIO", nullable = false, unique = true)
    private String nombreUsuario;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROL", nullable = false)
    private Rol rol;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Cliente cliente;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Grupo grupo;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Establecimiento establecimiento;

    public enum Rol {
        ESTABLECIMIENTO,
        GRUPO,
        CLIENTE
    }

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String password, Rol rol, String email) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Grupo getGrupo() {
        return grupo;
     }

     public void setGrupo(Grupo grupo) {
         this.grupo = grupo;
     }

    public Establecimiento getEstablecimiento() {
         return establecimiento;
     }

     public void setEstablecimiento(Establecimiento establecimiento) {
         this.establecimiento = establecimiento;
    }
}
