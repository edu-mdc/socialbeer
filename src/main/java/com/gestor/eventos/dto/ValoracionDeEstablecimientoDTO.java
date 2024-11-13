package com.gestor.eventos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public class ValoracionDeEstablecimientoDTO {

    private Long id;

    @NotEmpty
    @Size(min = 1, message = "La valoracion deberia tener a menos 1 caracter")
    private int puntuacion;

    @NotEmpty
    @Size(min = 10, message = "El comentario deberia tener a menos 10 caracteres")
    private String comentario;

    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaValoracion;

    private ClienteDTO cliente;

    private EstablecimientoDTO establecimiento;

    public ValoracionDeEstablecimientoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty
    @Size(min = 1, message = "La valoracion deberia tener a menos 1 caracter")
    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(@NotEmpty @Size(min = 1, message = "La valoracion deberia tener a menos 1 caracter") int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public @NotEmpty @Size(min = 10, message = "El comentario deberia tener a menos 10 caracteres") String getComentario() {
        return comentario;
    }

    public void setComentario(@NotEmpty @Size(min = 10, message = "El comentario deberia tener a menos 10 caracteres") String comentario) {
        this.comentario = comentario;
    }

    public @NotEmpty LocalDate getFechaValoracion() {
        return fechaValoracion;
    }

    public void setFechaValoracion(@NotEmpty LocalDate fechaValoracion) {
        this.fechaValoracion = fechaValoracion;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public EstablecimientoDTO getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(EstablecimientoDTO establecimiento) {
        this.establecimiento = establecimiento;
    }
}
