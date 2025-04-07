package com.crud.persona.crud.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.crud.persona.crud.modelo.ModeloUsuario;

@Repository
public interface IRepositorioUsuario extends JpaRepository<ModeloUsuario, Long> {
	ModeloUsuario findByCorreo(String nombreUsuario);
}
