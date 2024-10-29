package com.gestor.eventos.repository;

import com.gestor.eventos.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepositorioI extends JpaRepository<Grupo, Long> {
}
