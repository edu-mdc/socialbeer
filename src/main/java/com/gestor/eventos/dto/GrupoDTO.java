package com.gestor.eventos.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;
import java.util.Set;

@JsonIgnoreProperties({"valoracionDeGrupo", "eventos"})
public class GrupoDTO {

    private long id;


    @Size(min = 2, message = "El grupo deberia tener a menos 2 caracteres")
    private String grupo;


    @Size(min = 2, message = "El estilo deberia tener a menos 2 caracteres")
    private String estilo;


    @Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres")
    private String provincia;


    @Size(min = 4, message = "La poblacion deberia tener a menos 4 caracteres")
    private String poblacion;


    private double tarifa;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime duracion;


    private double desplazamiento;


    private int telefono;

    private UsuarioDTO usuario;

    private Set<ValoracionDeGrupoDTO> valoracionDeGrupo;

    private Set<EventoDTO> eventos;

    public GrupoDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @Size(min = 2, message = "El grupo deberia tener a menos 2 caracteres") String getGrupo() {
        return grupo;
    }

    public void setGrupo(@Size(min = 2, message = "El grupo deberia tener a menos 2 caracteres") String grupo) {
        this.grupo = grupo;
    }

    public @Size(min = 2, message = "El estilo deberia tener a menos 2 caracteres") String getEstilo() {
        return estilo;
    }

    public void setEstilo(@Size(min = 2, message = "El estilo deberia tener a menos 2 caracteres") String estilo) {
        this.estilo = estilo;
    }

    public @Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres") String getProvincia() {
        return provincia;
    }

    public void setProvincia(@Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres") String provincia) {
        this.provincia = provincia;
    }

    public @Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres") String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(@Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres") String poblacion) {
        this.poblacion = poblacion;
    }


    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }


    public double getDesplazamiento() {
        return desplazamiento;
    }

    public void setDesplazamiento(double desplazamiento) {
        this.desplazamiento = desplazamiento;
    }


    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public Set<ValoracionDeGrupoDTO> getValoracionDeGrupo() {
        return valoracionDeGrupo;
    }

    public void setValoracionDeGrupo(Set<ValoracionDeGrupoDTO> valoracionDeGrupo) {
        this.valoracionDeGrupo = valoracionDeGrupo;
    }

    public Set<EventoDTO> getEventos() {
        return eventos;
    }

    public void setEventos(Set<EventoDTO> eventos) {
        this.eventos = eventos;
    }
}
