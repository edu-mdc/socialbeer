package com.gestor.eventos.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "GRUPOS", uniqueConstraints = {@UniqueConstraint(columnNames = {"grupo"})})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GRUPO", nullable = false)
    private String grupo;

    @Column(name = "ESTILO")
    private String estilo;

    @Column(name = "PROVINCIA")
    private String provincia;

    @Column(name = "POBLACION")
    private String poblacion;

    @Column(name = "TARIFA")
    private double tarifa;

    @Column(name = "DURACION")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime duracion;

    @Column(name = "DESPLAZAMIENTO")
    private double desplazamiento;

    @Column(name = "TELEFONO")
    private int telefono;

    @Lob
    @Column(name = "FOTO")
    private byte[] foto;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_USUARIO")
    @JsonIgnoreProperties("grupo")
    private Usuario usuario;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ValoracionDeGrupo> valoracionGrupos = new HashSet<>();

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Evento> eventos = new HashSet<>();

    public Grupo() {
    }

    public Grupo(Long id, String grupo, String estilo, String provincia, String poblacion, double tarifa, LocalTime duracion, double desplazamiento, int telefono) {
        this.id = id;
        this.grupo = grupo;
        this.estilo = estilo;
        this.provincia = provincia;
        this.poblacion = poblacion;
        this.tarifa = tarifa;
        this.duracion = duracion;
        this.desplazamiento = desplazamiento;
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<ValoracionDeGrupo> getValoracionGrupos() {
        return valoracionGrupos;
    }

    public void setValoracionGrupos(Set<ValoracionDeGrupo> valoracionGrupos) {
        this.valoracionGrupos = valoracionGrupos;
    }

    public Set<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(Set<Evento> eventos) {
        this.eventos = eventos;
    }
}
