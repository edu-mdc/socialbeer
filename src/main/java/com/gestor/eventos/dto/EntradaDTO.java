package com.gestor.eventos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gestor.eventos.entities.Cliente;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;

@JsonIgnoreProperties({"cliente", "evento"})
public class EntradaDTO {

    private Long id;

    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaCompra;

    private Cliente cliente;

    private EventoDTO evento;

    public EntradaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(@NotEmpty LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }
}
