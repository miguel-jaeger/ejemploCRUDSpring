package com.crud.persona.crud.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.persona.crud.modelo.ModeloRol;
import com.crud.persona.crud.servicios.ServicioRol;

@RestController
@RequestMapping("/api/roles")
public class ControladorRestRoles {

    @Autowired
    private ServicioRol servicioRol;

    // Listar todos los roles
    @GetMapping
    public List<ModeloRol> listarRoles() {
        return servicioRol.listarRoles();
    }
}