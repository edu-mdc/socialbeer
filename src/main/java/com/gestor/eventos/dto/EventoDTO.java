package com.gestor.eventos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@JsonIgnoreProperties({"grupo", "establecimiento", "entradas"})
public class EventoDTO {

    private Long id;

    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaContratacion;

    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaEvento;

    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaEvento;

    @NotEmpty
    private double precio;

    @NotEmpty
    private  int ventas;

    @NotEmpty
    private String  estado;

    private String nombreGrupo;  // Nuevo campo
    private String nombreEstablecimiento;  // Nuevo campo

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getNombreEstablecimiento() {
        return nombreEstablecimiento;
    }

    public void setNombreEstablecimiento(String nombreEstablecimiento) {
        this.nombreEstablecimiento = nombreEstablecimiento;
    }

    private GrupoDTO grupo;

    private EstablecimientoDTO establecimiento;

    private Set<EntradaDTO> entradas;

    public EventoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(@NotEmpty LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public @NotEmpty LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(@NotEmpty LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public @NotEmpty LocalTime getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(@NotEmpty LocalTime horaEvento) {
        this.horaEvento = horaEvento;
    }

    @NotEmpty
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(@NotEmpty double precio) {
        this.precio = precio;
    }

    @NotEmpty
    public int getVentas() {
        return ventas;
    }

    public void setVentas(@NotEmpty int ventas) {
        this.ventas = ventas;
    }

    public @NotEmpty String getEstado() {
        return estado;
    }

    public void setEstado(@NotEmpty String estado) {
        this.estado = estado;
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

    public Set<EntradaDTO> getEntradas() {
        return entradas;
    }

    public void setEntradas(Set<EntradaDTO> entradas) {
        this.entradas = entradas;
    }
}
