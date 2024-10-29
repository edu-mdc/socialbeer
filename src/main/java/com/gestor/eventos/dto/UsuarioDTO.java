package com.gestor.eventos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gestor.eventos.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties({"cliente", "grupo", "establecimiento"})
public class UsuarioDTO {

    private Long id;

    @NotEmpty
    @Size(min = 5, message = "El usuario deberia tener a menos 5 caracteres")
    private String nombreUsuario;

    @NotEmpty
    @Size(min = 8, message = "La pass deberia tener a menos 8 caracteres")
    private String password;

    @NotEmpty
    private Usuario.Rol rol;

    @NotEmpty
    @Email
    private String email;

    private ClienteDTO cliente;

    private GrupoDTO grupo;

    private EstablecimientoDTO establecimiento;

    public UsuarioDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty @Size(min = 5, message = "El usuario deberia tener a menos 5 caracteres") String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(@NotEmpty @Size(min = 5, message = "El usuario deberia tener a menos 5 caracteres") String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario.Rol getRol() {
        return rol;
    }

    public void setRol(Usuario.Rol rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public GrupoDTO getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoDTO grupo) {
        this.grupo = grupo;
    }

    public EstablecimientoDTO getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(EstablecimientoDTO establecimiento) {
        this.establecimiento = establecimiento;
    }

}
