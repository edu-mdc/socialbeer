package com.gestor.eventos.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;
import java.util.Set;

@JsonIgnoreProperties({"valoracionDeGrupo", "eventos"})
public class GrupoDTO {

    private long id;

    @NotEmpty
    @Size(min = 2, message = "El grupo deberia tener a menos 2 caracteres")
    private String grupo;

    @NotEmpty
    @Size(min = 2, message = "El estilo deberia tener a menos 2 caracteres")
    private String estilo;

    @NotEmpty
    @Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres")
    private String provincia;

    @NotEmpty
    @Size(min = 4, message = "La poblacion deberia tener a menos 4 caracteres")
    private String poblacion;

    @NotEmpty
    private double tarifa;

    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime duracion;

    @NotEmpty
    private double desplazamiento;

    @NotEmpty
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

    public @NotEmpty @Size(min = 2, message = "El grupo deberia tener a menos 2 caracteres") String getGrupo() {
        return grupo;
    }

    public void setGrupo(@NotEmpty @Size(min = 2, message = "El grupo deberia tener a menos 2 caracteres") String grupo) {
        this.grupo = grupo;
    }

    public @NotEmpty @Size(min = 2, message = "El estilo deberia tener a menos 2 caracteres") String getEstilo() {
        return estilo;
    }

    public void setEstilo(@NotEmpty @Size(min = 2, message = "El estilo deberia tener a menos 2 caracteres") String estilo) {
        this.estilo = estilo;
    }

    public @NotEmpty @Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres") String getProvincia() {
        return provincia;
    }

    public void setProvincia(@NotEmpty @Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres") String provincia) {
        this.provincia = provincia;
    }

    public @NotEmpty @Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres") String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(@NotEmpty @Size(min = 4, message = "La provincia deberia tener a menos 4 caracteres") String poblacion) {
        this.poblacion = poblacion;
    }

    @NotEmpty
    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(@NotEmpty double tarifa) {
        this.tarifa = tarifa;
    }

    public @NotEmpty LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(@NotEmpty LocalTime duracion) {
        this.duracion = duracion;
    }

    @NotEmpty
    public double getDesplazamiento() {
        return desplazamiento;
    }

    public void setDesplazamiento(@NotEmpty double desplazamiento) {
        this.desplazamiento = desplazamiento;
    }

    @NotEmpty
    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(@NotEmpty int telefono) {
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
