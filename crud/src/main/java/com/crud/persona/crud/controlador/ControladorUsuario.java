package com.crud.persona.crud.controlador;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crud.persona.crud.modelo.ModeloRol;
import com.crud.persona.crud.modelo.ModeloUsuario;
import com.crud.persona.crud.servicios.ServicioRol;
import com.crud.persona.crud.servicios.ServicioUsuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;


@Controller

public class ControladorUsuario<Usuario> {
    @Autowired
    private ServicioUsuario servicioUsuario;
    @Autowired
    private ServicioRol servicioRol;
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
         // Usa la instancia inyectada para llamar al método
         List<ModeloRol> roles = servicioRol.listarRoles();
         modelo.addAttribute("roles", roles);
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
     
       // Obtener el usuario existente de la base de datos
       ModeloUsuario usuario = servicioUsuario.obtenerUsuarioPorId(idPersona);
       modelo.addAttribute("usuario", usuario);

       // Obtener la lista de roles desde la base de datos
       List<ModeloRol> roles = servicioRol.listarRoles();
       modelo.addAttribute("roles", roles);

       return "editarUsuario"; // Nombre de la vista Thymeleaf
   }
 
@PostMapping("usuarios/{id}")
public String actualizarUsuario(@PathVariable("id") Long idPersona, @ModelAttribute("usuario") ModeloUsuario usuario) {
    // Obtener el usuario existente de la base de datos
    ModeloUsuario usuarioExistente = servicioUsuario.obtenerUsuarioPorId(idPersona);

    if (usuarioExistente == null) {
        throw new RuntimeException("Usuario no encontrado");
    }

    // Actualizar los campos del usuario existente con los nuevos valores
    usuarioExistente.setNombre(usuario.getNombre());
    usuarioExistente.setApellido(usuario.getApellido());
    usuarioExistente.setCorreo(usuario.getCorreo());

    // Si estás actualizando el rol, asegúrate de que esté correctamente vinculado
    if (usuario.getRol() != null && usuario.getRol().getIdRol() != null) {
        ModeloRol rol = servicioRol.obtenerRolPorId(usuario.getRol().getIdRol());
        usuarioExistente.setRol(rol);
    }

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
   public String autenticarUsuario(@ModelAttribute("usuario") ModeloUsuario usuario, HttpServletRequest request
		   ) {
	   ModeloUsuario usuariovalida = servicioUsuario.autenticarUsuario(usuario.getCorreo(), usuario.getContrasena());
       String redirect = "autenticar404";
       if(usuariovalida!=null) { 
    	   //agregar session
    	   HttpSession session = request.getSession();
    	   session.setAttribute("usuario", usuariovalida); // guardar en la variable de sesion "username" el objeto usuariovalida
    	   redirect = "usuarios";
       }
       return "redirect:/"+redirect;
   }
   
   @GetMapping("/autenticar404")
   public String autenticarError(Model modelo) {
       return "autenticar404";
   }
   
   @GetMapping("/obtenerSession")
   public String getData(HttpSession session) {
       String username = (String) session.getAttribute("username");
       return "Hello, " + username;
   }
   
   @GetMapping("/cerrarSession")
   public String logout(HttpSession session) {
       // Invalidate the session
       session.invalidate();
       return "logged_out";
   }

}
