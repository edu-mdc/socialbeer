package com.gestor.eventos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

@JsonIgnoreProperties({"valoracionDeEstablecimiento", "eventos"})
public class EstablecimientoDTO {

    private long id;

    @NotEmpty
    @Size(min = 2, message = "El establecimiento deberia tener a menos 2 caracteres")
    private String establecimiento;

    @NotEmpty
    @Size(min = 8, message = "El CIF deberia tener a menos 8 caracteres")
    private String cif;

    @NotEmpty
    @Size(min = 2, message = "El aforo deberia tener a menos 2 caracteres")
    private int aforo;

    @NotEmpty
    @Size(min = 5, message = "La direccion deberia tener a menos 5 caracteres")
    private String direccion;

    @NotEmpty
    @Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres")
    private String provincia;

    @NotEmpty
    @Size(min = 4, message = "La poblacion deberia tener a menos 4 caracteres")
    private String poblacion;

    @NotEmpty
    @Size(min = 9, message = "El telefono deberia tener a menos 9 caracteres")
    private int telefono;

    private UsuarioDTO usuario;

    private Set<ValoracionDeEstablecimientoDTO> valoracionDeEstablecimiento;

    private Set<EventoDTO> eventos;

    public EstablecimientoDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotEmpty @Size(min = 2, message = "El establecimiento deberia tener a menos 2 caracteres") String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(@NotEmpty @Size(min = 2, message = "El establecimiento deberia tener a menos 2 caracteres") String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public @NotEmpty @Size(min = 8, message = "El CIF deberia tener a menos 8 caracteres") String getCif() {
        return cif;
    }

    public void setCif(@NotEmpty @Size(min = 8, message = "El CIF deberia tener a menos 8 caracteres") String cif) {
        this.cif = cif;
    }

    @NotEmpty
    @Size(min = 2, message = "El aforo deberia tener a menos 2 caracteres")
    public int getAforo() {
        return aforo;
    }

    public void setAforo(@NotEmpty @Size(min = 2, message = "El aforo deberia tener a menos 2 caracteres") int aforo) {
        this.aforo = aforo;
    }

    public @NotEmpty @Size(min = 5, message = "La direccion deberia tener a menos 5 caracteres") String getDireccion() {
        return direccion;
    }

    public void setDireccion(@NotEmpty @Size(min = 5, message = "La direccion deberia tener a menos 5 caracteres") String direccion) {
        this.direccion = direccion;
    }

    public @NotEmpty @Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres") String getProvincia() {
        return provincia;
    }

    public void setProvincia(@NotEmpty @Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres") String provincia) {
        this.provincia = provincia;
    }

    public @NotEmpty @Size(min = 4, message = "La poblacion deberia tener a menos 4 caracteres") String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(@NotEmpty @Size(min = 4, message = "La poblacion deberia tener a menos 4 caracteres") String poblacion) {
        this.poblacion = poblacion;
    }

    @NotEmpty
    @Size(min = 9, message = "El telefono deberia tener a menos 9 caracteres")
    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(@NotEmpty @Size(min = 9, message = "El telefono deberia tener a menos 9 caracteres") int telefono) {
        this.telefono = telefono;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public Set<ValoracionDeEstablecimientoDTO> getValoracionDeEstablecimiento() {
        return valoracionDeEstablecimiento;
    }

    public void setValoracionDeEstablecimiento(Set<ValoracionDeEstablecimientoDTO> valoracionDeEstablecimiento) {
        this.valoracionDeEstablecimiento = valoracionDeEstablecimiento;
    }

    public Set<EventoDTO> getEventos() {
        return eventos;
    }

    public void setEventos(Set<EventoDTO> eventos) {
        this.eventos = eventos;
    }

}
