package com.gestor.eventos.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ESTABLECIMIENTOS", uniqueConstraints = {@UniqueConstraint(columnNames = {"establecimiento"})})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Establecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ESTABLECIMIENTO", nullable = false)
    private String establecimiento;

    @Column(name = "CIF", nullable = false, unique = true)
    private String cif;

    @Column(name = "AFORO")
    private int aforo;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "PROVINCIA")
    private String provincia;

    @Column(name = "POBLACION")
    private String poblacion;

    @Column(name = "TELEFONO")
    private int telefono;

    @Lob
    @Column(name = "FOTO")
    private byte[] foto;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_USUARIO")
    @JsonIgnoreProperties("establecimiento")
    private Usuario usuario;

    @OneToMany(mappedBy = "establecimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ValoracionDeEstablecimiento> valoracionEstablecimientos = new HashSet<>();

    @OneToMany(mappedBy = "establecimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Evento> eventos = new HashSet<>();

    public Establecimiento() {
    }

    public Establecimiento(Long id, String establecimiento, String cif, int aforo, String direccion, String provincia, String poblacion, int telefono) {
        this.id = id;
        this.establecimiento = establecimiento;
        this.cif = cif;
        this.aforo = aforo;
        this.direccion = direccion;
        this.provincia = provincia;
        this.poblacion = poblacion;
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public Set<ValoracionDeEstablecimiento> getValoracionEstablecimientos() {
        return valoracionEstablecimientos;
    }

    public void setValoracionEstablecimientos(Set<ValoracionDeEstablecimiento> valoracionEstablecimientos) {
        this.valoracionEstablecimientos = valoracionEstablecimientos;
    }

    public Set<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(Set<Evento> eventos) {
        this.eventos = eventos;
    }
}
