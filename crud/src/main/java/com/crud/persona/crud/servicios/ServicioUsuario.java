package com.crud.persona.crud.servicios;

import java.util.ArrayList;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.persona.crud.modelo.ModeloUsuario;
import com.crud.persona.crud.repositorios.IRepositorioUsuario;

@Service
public class ServicioUsuario {

	@Autowired
	IRepositorioUsuario repositorioUsuario;

	public ArrayList<ModeloUsuario> listarUsuarios() {
		return (ArrayList<ModeloUsuario>) repositorioUsuario.findAll();
	}

	public ModeloUsuario guardarUsuario(ModeloUsuario usuario) {
		ModeloUsuario existente = repositorioUsuario.findByCorreo(usuario.getCorreo());
	    if (existente!=null) {
	        return null; // Ya existe el correo
	    }

		return repositorioUsuario.save(usuario);
	}

	public ModeloUsuario actualizarUsuario(ModeloUsuario usuario) {
		return repositorioUsuario.save(usuario);
	}

	public void eliminarUsuario(ModeloUsuario usuario) {
		repositorioUsuario.deleteById(usuario.getIdPersona());
	}

	public ModeloUsuario obtenerUsuarioPorId(Long id) {
		//return repositorioUsuario.findById(id).orElse(null);
		return repositorioUsuario.findByIdWithRol(id)
		.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	}
	
	public ModeloUsuario autenticarUsuario(String nombreUsuario, String password) {
        ModeloUsuario usuarioOpt = repositorioUsuario.findByCorreo(nombreUsuario);
        //password -> clave del formulario
        // usuarioOpt.getContrasena() -> clave de base de datos
        boolean valida = false;
        if (usuarioOpt!=null) {
        	valida = BCrypt.checkpw(password, usuarioOpt.getContrasena());
        }
        return valida?usuarioOpt:null;
    }
}
