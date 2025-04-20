package com.crud.persona.crud.servicios;

import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.persona.crud.modelo.ModeloRol;
import com.crud.persona.crud.repositorios.IRepositorioRol;

@Service
public class ServicioRol {

	@Autowired
	IRepositorioRol repositorioRol;

	// Método para listar todos los roles
	public List<ModeloRol> listarRoles() {
        return repositorioRol.findAll();
    }

	 // Método para obtener un rol por su ID
	 public ModeloRol obtenerRolPorId(Long idRol) {
        return repositorioRol.findById(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + idRol));
    }
	
}
