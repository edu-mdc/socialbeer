package com.gestor.eventos.repository;

import com.gestor.eventos.entities.ValoracionDeGrupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValoracionDeGrupoRepositorioI extends JpaRepository<ValoracionDeGrupo,Long> {

    public List<ValoracionDeGrupo> findByGrupoId(long grupoId);
}
