package com.gestor.eventos.repository;

import com.gestor.eventos.entities.Establecimiento;
import com.gestor.eventos.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrupoRepositorioI extends JpaRepository<Grupo, Long> {
    Optional<Grupo> findByUsuarioId(Long usuarioId);
}
