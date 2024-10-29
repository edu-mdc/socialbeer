package com.gestor.eventos.repository;

import com.gestor.eventos.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepositorioI extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByUsuarioId(Long usuarioId);

    boolean existsByDni(String dni);

    Optional<Cliente> findByDni(String dni);
}
