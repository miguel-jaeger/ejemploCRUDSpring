package com.irojas.demojwt.Auth;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.irojas.demojwt.User.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }
    
    //------------------------------
    //Adicionar
    @PostMapping
    public User salvarUsuario(@RequestBody User usuario) {
        usuario.setPassword(this.claveEncriptada(usuario.getPassword()));      
        return this.authService.guardarUsuario(usuario);
	}   
	
	@PutMapping
	public User actualizarUsuario(@RequestBody User usuario) {
	    usuario.setPassword(this.claveEncriptada(usuario.getPassword()));      
	    return this.authService.actualizarUsuario(usuario);
	}
	
	@DeleteMapping
	public void eliminarUsuario(@RequestBody User usuario) {
	    this.authService.eliminarUsuario(usuario);
	}
	
	 public String claveEncriptada(String clave) {
		   return BCrypt.hashpw(clave, BCrypt.gensalt());
	   }
	 
	 @PostMapping("/autenticar")
	 public boolean autenticarUsuario(@RequestBody User usuario) {   
	     return this.authService.autenticarUsuario(usuario.getUsername(), usuario.getPassword());
	}
}
