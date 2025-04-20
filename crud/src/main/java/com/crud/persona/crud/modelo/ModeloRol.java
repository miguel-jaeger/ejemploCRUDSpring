package com.crud.persona.crud.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class ModeloRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    // Constructor vacío
    public ModeloRol() {
    }

    // Constructor con parámetros
    public ModeloRol(String descripcion) {
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Rol [idRol=" + idRol + ", descripcion=" + descripcion + "]";
    }
}