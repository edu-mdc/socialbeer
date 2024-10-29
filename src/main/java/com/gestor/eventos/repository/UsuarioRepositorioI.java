package com.gestor.eventos.repository;

import com.gestor.eventos.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorioI extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario); // Cambiado a camelCase

    boolean existsByNombreUsuario(String nombreUsuario); // Cambiado a camelCase

    boolean existsByEmail(String email);
}
