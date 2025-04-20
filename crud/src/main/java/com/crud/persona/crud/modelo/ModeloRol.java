package com.crud.persona.crud.modelo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ModeloRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;
    
    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
    private List<ModeloUsuario> usuariosCollection;

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
    
    public List<ModeloUsuario> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(List<ModeloUsuario> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @Override
    public String toString() {
        return "Rol [idRol=" + idRol + ", descripcion=" + descripcion + "]";
    }
}