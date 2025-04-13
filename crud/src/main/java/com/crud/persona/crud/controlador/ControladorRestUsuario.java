package com.crud.persona.crud.controlador;

import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.persona.crud.modelo.ModeloUsuario;
import com.crud.persona.crud.repositorios.IRepositorioUsuario;
import com.crud.persona.crud.servicios.ServicioUsuario;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/usuarios")
public class ControladorRestUsuario {

    @Autowired
    private ServicioUsuario servicioUsuario;

    //Listar
    @GetMapping
    public ArrayList<ModeloUsuario> listarUsuarios() {
        return servicioUsuario.listarUsuarios();
    }
//Adicionar
    @PostMapping
    public ModeloUsuario salvarUsuario(@RequestBody ModeloUsuario usuario) {
        usuario.setContrasena(this.claveEncriptada(usuario.getContrasena()));      
        return this.servicioUsuario.guardarUsuario(usuario);
}   

@PutMapping
public ModeloUsuario actualizarUsuario(@RequestBody ModeloUsuario usuario) {
    usuario.setContrasena(this.claveEncriptada(usuario.getContrasena()));      
    return this.servicioUsuario.actualizarUsuario(usuario);
}

@DeleteMapping
public void eliminarUsuario(@RequestBody ModeloUsuario usuario) {
    this.servicioUsuario.eliminarUsuario(usuario);
}

 public String claveEncriptada(String clave) {
	   return BCrypt.hashpw(clave, BCrypt.gensalt());
   }
}
