package com.crud.persona.crud.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.crud.persona.crud.modelo.ModeloUsuario;
import com.crud.persona.crud.servicios.ServicioUsuario;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ControladorUsuario {
    @Autowired
    private ServicioUsuario servicioUsuario;
    
    //Listar
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
        
        
}
