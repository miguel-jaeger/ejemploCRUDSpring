package com.crud.persona.crud.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class ModeloUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;  

    @Column(name = "correo", unique=true)
    private String correo;
    
    @Column(name = "contrasena")
    private String contrasena;

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

	@Override
	public String toString() {
		return "ModeloUsuario [idPersona=" + idPersona + ", nombre=" + nombre + ", apellido=" + apellido + ", correo="
				+ correo + ", contrasena=" + contrasena + "]";
	}

       


}
