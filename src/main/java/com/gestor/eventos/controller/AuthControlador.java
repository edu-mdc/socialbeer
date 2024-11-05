package com.gestor.eventos.controller;

import com.gestor.eventos.Security.JwtAuthResponseDTO;
import com.gestor.eventos.Security.JwtTokenProvider;
import com.gestor.eventos.dto.LoginDTO;
import com.gestor.eventos.dto.RegistroDTO;
import com.gestor.eventos.dto.UsuarioDTO;
import com.gestor.eventos.entities.Usuario;
import com.gestor.eventos.repository.UsuarioRepositorioI;
import com.gestor.eventos.services.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepositorioI usuarioRepositorioI;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/iniciarSesion")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getNombreUsuario(), loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generarToken(authentication);
        String rol = authentication.getAuthorities().iterator().next().getAuthority();

        // Obtener el usuario autenticado de la base de datos
        Usuario usuario = usuarioRepositorioI.findByNombreUsuario(loginDTO.getNombreUsuario())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return ResponseEntity.ok(new JwtAuthResponseDTO(token, rol, usuario.getId(), usuario.getNombreUsuario()));
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        if(usuarioRepositorioI.existsByNombreUsuario(usuarioDTO.getNombreUsuario())){
            return  new ResponseEntity<>(Map.of("isSuccess", false, "message", "nombre de usuario existe"), HttpStatus.BAD_REQUEST);
        }

        if(usuarioRepositorioI.existsByEmail(usuarioDTO.getEmail())){
            return  new ResponseEntity<>(Map.of("isSuccess", false, "message", "email de usuario existe"), HttpStatus.BAD_REQUEST);
        }

        /*Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setRol(usuarioDTO.getRol());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setEmail(usuarioDTO.getEmail());*/

        usuarioDTO.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        UsuarioDTO nuevoUsuario = usuarioServicio.crearUsuario(usuarioDTO);

        //usuarioRepositorioI.save(usuario);
        return new ResponseEntity<>(Map.of("isSuccess", true, "message", "Usuario registrado exitosamente"), HttpStatus.OK);
    }
}
