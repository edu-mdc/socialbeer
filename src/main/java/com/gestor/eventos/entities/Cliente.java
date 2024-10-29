package com.gestor.eventos.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CLIENTES")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "APELLIDO_1", nullable = false)
    private String apellido_1;

    @Column(name = "APELLIDO_2")
    private String apellido_2;

    @Column(name = "DNI", nullable = false, unique = true)
    private String dni;

    @Column(name = "FECHA_NACIMIENTO")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaNacimiento;

    @Column(name = "TELEFONO")
    private int telefono;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "PROVINCIA")
    private String provincia;

    @Column(name = "POBLACION")
    private String poblacion;

    @Lob
    @Column(name = "FOTO")
    private byte[] foto;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_USUARIO")
    @JsonIgnoreProperties("cliente")
    private Usuario usuario;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ValoracionDeGrupo> valoracionDeGrupos = new HashSet<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ValoracionDeEstablecimiento> valoracionDeEstablecimientos = new HashSet<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Entrada> entradas = new HashSet<>();

    public Cliente() {
    }

    public Cliente(String nombre, String apellido_1, String apellido_2, String dni, LocalDate fechaNacimiento, int telefono, String direccion, String provincia, String poblacion) {
        this.nombre = nombre;
        this.apellido_1 = apellido_1;
        this.apellido_2 = apellido_2;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.provincia = provincia;
        this.poblacion = poblacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_1() {
        return apellido_1;
    }

    public void setApellido_1(String apellido_1) {
        this.apellido_1 = apellido_1;
    }

    public String getApellido_2() {
        return apellido_2;
    }

    public void setApellido_2(String apellido_2) {
        this.apellido_2 = apellido_2;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
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

    public Set<ValoracionDeGrupo> getValoracionDeGrupos() {
        return valoracionDeGrupos;
    }

    public void setValoracionDeGrupos(Set<ValoracionDeGrupo> valoracionDeGrupos) {
        this.valoracionDeGrupos = valoracionDeGrupos;
    }

    public Set<ValoracionDeEstablecimiento> getValoracionDeEstablecimientos() {
        return valoracionDeEstablecimientos;
    }

    public void setValoracionDeEstablecimientos(Set<ValoracionDeEstablecimiento> valoracionDeEstablecimientos) {
        this.valoracionDeEstablecimientos = valoracionDeEstablecimientos;
    }

    public Set<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(Set<Entrada> entradas) {
        this.entradas = entradas;
    }
}
