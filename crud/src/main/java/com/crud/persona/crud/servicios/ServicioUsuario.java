package com.crud.persona.crud.servicios;
import java.util.ArrayList;

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
        return repositorioUsuario.save(usuario);
    }

    
    public ModeloUsuario actualizarUsuario(ModeloUsuario usuario) {
        return repositorioUsuario.save(usuario);
    }

    public void eliminarUsuario(ModeloUsuario usuario) {
         repositorioUsuario.deleteById(usuario.getIdPersona());
    }
}
