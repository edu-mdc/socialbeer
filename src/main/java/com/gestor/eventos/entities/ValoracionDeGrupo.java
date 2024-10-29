package com.gestor.eventos.entities;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "VALORACIONES_GRUPOS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ValoracionDeGrupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PUNTUACION", nullable = false)
    private int puntuacion;

    @Column(name = "COMENTARIO")
    private String comentario;

    @Column(name = "FECHA_VALORACION", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaValoracion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE")
    @JsonBackReference
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO")
    @JsonBackReference
    private Grupo grupo;

    public ValoracionDeGrupo() {
    }

    public ValoracionDeGrupo(int puntuacion, String comentario, LocalDate fechaValoracion, long id) {
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fechaValoracion = fechaValoracion;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFechaValoracion() {
        return fechaValoracion;
    }

    public void setFechaValoracion(LocalDate fechaValoracion) {
        this.fechaValoracion = fechaValoracion;
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
}
