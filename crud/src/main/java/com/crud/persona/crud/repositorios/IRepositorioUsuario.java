package com.crud.persona.crud.repositorios;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.crud.persona.crud.modelo.ModeloUsuario;

@Repository
public interface IRepositorioUsuario extends JpaRepository<ModeloUsuario, Long> {
	ModeloUsuario findByCorreo(String nombreUsuario);

	@Query("SELECT u FROM ModeloUsuario u JOIN FETCH u.rol WHERE u.idPersona = :id")
    Optional<ModeloUsuario> findByIdWithRol(@Param("id") Long id);
}
