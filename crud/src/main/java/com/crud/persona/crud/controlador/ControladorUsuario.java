package com.crud.persona.crud.controlador;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.crud.persona.crud.modelo.ModeloUsuario;
import com.crud.persona.crud.servicios.ServicioUsuario;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ControladorUsuario<Usuario> {
    @Autowired
    private ServicioUsuario servicioUsuario;
    // redirecciona al home de la app
    @GetMapping("/")
    public String redirectToUsuarios() {
        return "redirect:/usuarios";
    }
    // Listar
    @GetMapping("/usuarios")
    public String listarUsuarios(Model modelo) {
        modelo.addAttribute("usuarios", servicioUsuario.listarUsuarios());
        return "usuarios";
    }

    @GetMapping("usuarios/nuevo")
    public String mostrarFormularioAdicionar(Model modelo) {
        ModeloUsuario usuario = new ModeloUsuario();
        modelo.addAttribute("usuario", usuario);
        return "adicionarUsuario";
    }

    // Guardar usuario
   @PostMapping("usuarios/guardar")
   public String guardarUsuario(@ModelAttribute("usuario") ModeloUsuario usuario) {
	   usuario.setContrasena(this.claveEncriptada(usuario.getContrasena()));
       servicioUsuario.guardarUsuario(usuario);
       return "redirect:/usuarios";
   }
   
   @GetMapping("usuarios/editar/{id}")
   public String mostrarFormularioEditar(@RequestParam Long idPersona, Model modelo) {
       modelo.addAttribute("usuario", servicioUsuario.obtenerUsuarioPorId(idPersona));
       return "editarUsuario";
   }
 
   @PostMapping("usuarios/{id}")
   public String actualizarUsuario(@RequestParam Long idPersona, @ModelAttribute("usuario") ModeloUsuario usuario) {
       // Obtener el usuario existente de la base de datos
       ModeloUsuario usuarioExistente = servicioUsuario.obtenerUsuarioPorId(idPersona);
       // Actualizar los campos del usuario existente con los nuevos valores
      usuarioExistente.setIdPersona(idPersona);
       usuarioExistente.setNombre(usuario.getNombre());
       usuarioExistente.setApellido(usuario.getApellido());
       usuarioExistente.setCorreo(usuario.getCorreo());
      // usuarioExistente.setContrasena(usuario.getContrasena());
       // Guardar el usuario actualizado en la base de datos
       servicioUsuario.actualizarUsuario(usuarioExistente);
       return "redirect:/usuarios";
    
   }

   @GetMapping("usuarios/eliminar/{id}")
   public String eliminarUsuario(@RequestParam Long idPersona) {
     ModeloUsuario usuario = new ModeloUsuario();   
         usuario.setIdPersona(idPersona);
       servicioUsuario.eliminarUsuario(usuario);
       return "redirect:/usuarios";
   }
 
   public String claveEncriptada(String clave) {
	   return BCrypt.hashpw(clave, BCrypt.gensalt());
   }

   @GetMapping("usuarios/autenticar")
   public String mostrarAutenticarUsuario(Model modelo) {
       ModeloUsuario usuario = new ModeloUsuario();
       modelo.addAttribute("usuario", usuario);
       return "autenticarUsuario";
   }
   
   @PostMapping("usuarios/autenticarUsuario")
   public String autenticarUsuario(@ModelAttribute("usuario") ModeloUsuario usuario) {
       boolean valida = servicioUsuario.autenticarUsuario(usuario.getCorreo(), usuario.getContrasena());
       String redirect = "autenticar404";
       if(valida) redirect = "usuarios";
       return "redirect:/"+redirect;
   }
   
   @GetMapping("/autenticar404")
   public String autenticarError(Model modelo) {
       return "autenticar404";
   }

}
