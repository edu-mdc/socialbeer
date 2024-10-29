package com.gestor.eventos.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

@JsonIgnoreProperties({"valoracionDeGrupos", "valoracionDeEstablecimientos", "entradas"})
public class ClienteDTO {

    private long id;

    @NotEmpty
    @Size(min = 3, message = "El nombre deberia tener a menos 3 caracteres")
    private String nombre;

    @NotEmpty
    @Size(min = 3, message = "El apellido1 deberia tener a menos 5 caracteres")
    private String apellido_1;

    private String apellido_2;

    @NotEmpty
    @Size(min = 9, message = "El dni deberia tener a menos 9 caracteres")
    private String dni;

    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaNacimiento;

    @NotEmpty
    @Size(min = 9, message = "El telefono deberia tener a menos 9 caracteres")
    private int telefono;

    @NotEmpty
    @Size(min = 5, message = "La direccion deberia tener a menos 5 caracteres")
    private String direccion;

    @NotEmpty
    @Size(min = 4, message = "La direccion deberia tener a menos 4 caracteres")
    private String provincia;

    @NotEmpty
    @Size(min = 4, message = "La direccion deberia tener a menos 4 caracteres")
    private String poblacion;

    private UsuarioDTO usuario;

    private Set<ValoracionDeGrupoDTO> valoracionDeGrupos;

    private Set<ValoracionDeEstablecimientoDTO> valoracionDeEstablecimientos;

    private Set<EntradaDTO> entradas;

    public ClienteDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotEmpty @Size(min = 3, message = "El nombre deberia tener a menos 3 caracteres") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotEmpty @Size(min = 3, message = "El nombre deberia tener a menos 3 caracteres") String nombre) {
        this.nombre = nombre;
    }

    public @NotEmpty @Size(min = 3, message = "El apellido1 deberia tener a menos 5 caracteres") String getApellido_1() {
        return apellido_1;
    }

    public void setApellido_1(@NotEmpty @Size(min = 3, message = "El apellido1 deberia tener a menos 5 caracteres") String apellido_1) {
        this.apellido_1 = apellido_1;
    }

    public String getApellido_2() {
        return apellido_2;
    }

    public void setApellido_2(String apellido_2) {
        this.apellido_2 = apellido_2;
    }

    public @NotEmpty @Size(min = 9, message = "El dni deberia tener a menos 9 caracteres") String getDni() {
        return dni;
    }

    public void setDni(@NotEmpty @Size(min = 9, message = "El dni deberia tener a menos 9 caracteres") String dni) {
        this.dni = dni;
    }

    public @NotEmpty LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(@NotEmpty LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @NotEmpty
    @Size(min = 9, message = "El telefono deberia tener a menos 9 caracteres")
    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(@NotEmpty @Size(min = 9, message = "El telefono deberia tener a menos 9 caracteres") int telefono) {
        this.telefono = telefono;
    }

    public @NotEmpty @Size(min = 5, message = "La direccion deberia tener a menos 5 caracteres") String getDireccion() {
        return direccion;
    }

    public void setDireccion(@NotEmpty @Size(min = 5, message = "La direccion deberia tener a menos 5 caracteres") String direccion) {
        this.direccion = direccion;
    }

    public @NotEmpty @Size(min = 5, message = "La direccion deberia tener a menos 5 caracteres") String getProvincia() {
        return provincia;
    }

    public void setProvincia(@NotEmpty @Size(min = 5, message = "La direccion deberia tener a menos 5 caracteres") String provincia) {
        this.provincia = provincia;
    }

    public @NotEmpty @Size(min = 5, message = "La direccion deberia tener a menos 5 caracteres") String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(@NotEmpty @Size(min = 5, message = "La direccion deberia tener a menos 5 caracteres") String poblacion) {
        this.poblacion = poblacion;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public Set<ValoracionDeGrupoDTO> getValoracionDeGrupos() {
        return valoracionDeGrupos;
    }

    public void setValoracionDeGrupos(Set<ValoracionDeGrupoDTO> valoracionDeGrupos) {
        this.valoracionDeGrupos = valoracionDeGrupos;
    }

    public Set<ValoracionDeEstablecimientoDTO> getValoracionDeEstablecimientos() {
        return valoracionDeEstablecimientos;
    }

    public void setValoracionDeEstablecimientos(Set<ValoracionDeEstablecimientoDTO> valoracionDeEstablecimientos) {
        this.valoracionDeEstablecimientos = valoracionDeEstablecimientos;
    }

    public Set<EntradaDTO> getEntradas() {
        return entradas;
    }

    public void setEntradas(Set<EntradaDTO> entradas) {
        this.entradas = entradas;
    }
}
