package com.gestor.eventos.Security;

import com.gestor.eventos.entities.Usuario;
import com.gestor.eventos.repository.UsuarioRepositorioI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collections;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UsuarioRepositorioI usuarioRepositorioI;

    @Override
    public UserDetails loadUserByUsername(String nombre_usuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorioI.findByNombreUsuario(nombre_usuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + nombre_usuario));

        return new User(usuario.getNombreUsuario(), usuario.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol())));
    }
}
