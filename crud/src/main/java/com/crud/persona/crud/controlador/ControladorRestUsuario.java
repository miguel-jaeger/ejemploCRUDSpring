// CRUD - Controlador REST para la entidad Usuario

package com.crud.persona.crud.controlador;

import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crud.persona.crud.modelo.ModeloRol;
import com.crud.persona.crud.modelo.ModeloUsuario;
import com.crud.persona.crud.servicios.ServicioRol;
import com.crud.persona.crud.servicios.ServicioUsuario;

@RestController
@RequestMapping("/api/usuarios")
public class ControladorRestUsuario {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private ServicioRol servicioRol;

    // Listar todos los usuarios
    @GetMapping
    public ArrayList<ModeloUsuario> listarUsuarios() {
        return servicioUsuario.listarUsuarios();
    }

    // Adicionar un nuevo usuario
    @PostMapping
    public ResponseEntity<ModeloUsuario> salvarUsuario(@RequestBody ModeloUsuario usuario) {
        // Encriptar la contraseña
        usuario.setContrasena(this.claveEncriptada(usuario.getContrasena()));

        // Validar y asignar el rol
        if (usuario.getRol() != null && usuario.getRol().getIdRol() != null) {
            ModeloRol rol = servicioRol.obtenerRolPorId(usuario.getRol().getIdRol());
            if (rol == null) {
                return ResponseEntity.badRequest().body(null); // Rol no encontrado
            }
            usuario.setRol(rol);
        }

        // Guardar el usuario
        ModeloUsuario usuarioGuardado = servicioUsuario.guardarUsuario(usuario);
        return ResponseEntity.ok(usuarioGuardado);
    }

    // Actualizar un usuario existente
    @PutMapping
    public ResponseEntity<ModeloUsuario> actualizarUsuario(@RequestBody ModeloUsuario usuario) {
        // Validar que el usuario exista
        ModeloUsuario usuarioExistente = servicioUsuario.obtenerUsuarioPorId(usuario.getIdPersona());
        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build(); // Usuario no encontrado
        }

        // Actualizar los campos del usuario
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setCorreo(usuario.getCorreo());

        // Encriptar la contraseña si se proporciona una nueva
        if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
            usuarioExistente.setContrasena(this.claveEncriptada(usuario.getContrasena()));
        }

        // Validar y asignar el rol
        if (usuario.getRol() != null && usuario.getRol().getIdRol() != null) {
            ModeloRol rol = servicioRol.obtenerRolPorId(usuario.getRol().getIdRol());
            if (rol == null) {
                return ResponseEntity.badRequest().body(null); // Rol no encontrado
            }
            usuarioExistente.setRol(rol);
        }

        // Guardar los cambios
        ModeloUsuario usuarioActualizado = servicioUsuario.actualizarUsuario(usuarioExistente);
        return ResponseEntity.ok(usuarioActualizado);
    }

    // Eliminar un usuario
    @DeleteMapping
    public ResponseEntity<Void> eliminarUsuario(@RequestBody ModeloUsuario usuario) {
        // Validar que el usuario exista
        ModeloUsuario usuarioExistente = servicioUsuario.obtenerUsuarioPorId(usuario.getIdPersona());
        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build(); // Usuario no encontrado
        }

        // Eliminar el usuario
        servicioUsuario.eliminarUsuario(usuarioExistente);
        return ResponseEntity.noContent().build();
    }

    // Método para encriptar contraseñas
    private String claveEncriptada(String clave) {
        return BCrypt.hashpw(clave, BCrypt.gensalt());
    }
}