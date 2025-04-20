package com.crud.persona.crud.repositorios;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.persona.crud.modelo.ModeloRol;

@Repository
public interface IRepositorioRol extends JpaRepository<ModeloRol, Long> {
	ModeloRol findByDescripcion(String Descripcion);
}
