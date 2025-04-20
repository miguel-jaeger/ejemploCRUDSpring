package com.crud.persona.crud.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class ModeloUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "correo", nullable = false, unique = true, length = 255)
    private String correo;

    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    // Relación muchos a uno con Rol
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ModeloRol rol;

    // Constructor vacío
    public ModeloUsuario() {
    }

    // Constructor con parámetros
    public ModeloUsuario(String nombre, String apellido, String correo, String contrasena, ModeloRol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // Getters y Setters
    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public ModeloRol getRol() {
        return rol;
    }

    public void setRol(ModeloRol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "ModeloUsuario [idPersona=" + idPersona + ", nombre=" + nombre + ", apellido=" + apellido + ", correo="
                + correo + ", contrasena=" + contrasena + ", rol=" + rol + "]";
    }
}