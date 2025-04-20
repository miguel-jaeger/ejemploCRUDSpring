package com.irojas.demojwt.Auth;

import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.irojas.demojwt.Jwt.JwtService;
import com.irojas.demojwt.User.Role;
import com.irojas.demojwt.User.User;
import com.irojas.demojwt.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername());//.orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();

    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode( request.getPassword()))
            .nombre(request.getNombre())
            .apellido(request.getApellido())
            //.role(Role.USER)
            .build();

        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
        
    }
    
    public ArrayList<User> listarUsuarios() {
		return (ArrayList<User>) userRepository.findAll();
	}

	public User guardarUsuario(User usuario) {
		User existente = userRepository.findByUsername(usuario.getUsername());
	    if (existente!=null) {
	        return null; // Ya existe el correo
	    }

		return userRepository.save(usuario);
	}

	public User actualizarUsuario(User usuario) {
		return userRepository.save(usuario);
	}

	public void eliminarUsuario(User usuario) {
		userRepository.deleteById(usuario.getIdPersona());
	}

	public User obtenerUsuarioPorId(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public boolean autenticarUsuario(String nombreUsuario, String password) {
		User usuarioOpt = userRepository.findByUsername(nombreUsuario);
        //password -> clave del formulario
        // usuarioOpt.getContrasena() -> clave de base de datos
        if (usuarioOpt!=null) {
            return BCrypt.checkpw(password, usuarioOpt.getPassword());
        }
        return false;
    }

}
