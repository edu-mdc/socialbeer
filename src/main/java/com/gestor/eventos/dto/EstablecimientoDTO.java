package com.gestor.eventos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

@JsonIgnoreProperties({"valoracionDeEstablecimiento", "eventos"})
public class EstablecimientoDTO {

    private long id;


    @Size(min = 2, message = "El establecimiento deberia tener a menos 2 caracteres")
    private String establecimiento;


    @Size(min = 8, message = "El CIF deberia tener a menos 8 caracteres")
    private String cif;


    @Size(min = 2, message = "El aforo deberia tener a menos 2 caracteres")
    private int aforo;


    @Size(min = 5, message = "La direccion deberia tener a menos 5 caracteres")
    private String direccion;


    @Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres")
    private String provincia;


    @Size(min = 4, message = "La poblacion deberia tener a menos 4 caracteres")
    private String poblacion;


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

    public @Size(min = 2, message = "El establecimiento deberia tener a menos 2 caracteres") String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(@Size(min = 2, message = "El establecimiento deberia tener a menos 2 caracteres") String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public @Size(min = 8, message = "El CIF deberia tener a menos 8 caracteres") String getCif() {
        return cif;
    }

    public void setCif(@Size(min = 8, message = "El CIF deberia tener a menos 8 caracteres") String cif) {
        this.cif = cif;
    }


    @Size(min = 2, message = "El aforo deberia tener a menos 2 caracteres")
    public int getAforo() {
        return aforo;
    }

    public void setAforo(@Size(min = 2, message = "El aforo deberia tener a menos 2 caracteres") int aforo) {
        this.aforo = aforo;
    }

    public @Size(min = 5, message = "La direccion deberia tener a menos 5 caracteres") String getDireccion() {
        return direccion;
    }

    public void setDireccion(@Size(min = 5, message = "La direccion deberia tener a menos 5 caracteres") String direccion) {
        this.direccion = direccion;
    }

    public @Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres") String getProvincia() {
        return provincia;
    }

    public void setProvincia(@Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres") String provincia) {
        this.provincia = provincia;
    }

    public @Size(min = 4, message = "La poblacion deberia tener a menos 4 caracteres") String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(@Size(min = 4, message = "La poblacion deberia tener a menos 4 caracteres") String poblacion) {
        this.poblacion = poblacion;
    }


    @Size(min = 9, message = "El telefono deberia tener a menos 9 caracteres")
    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(@Size(min = 9, message = "El telefono deberia tener a menos 9 caracteres") int telefono) {
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
