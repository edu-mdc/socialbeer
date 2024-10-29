package com.gestor.eventos.repository;

import com.gestor.eventos.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventoRepositorioI extends JpaRepository<Evento,Long> {
    public List<Evento> findByGrupoId(long grupoId);
    public List<Evento> findByEstablecimientoId(long establecimientoId);
    List<Evento> findByGrupoIdAndFechaEvento(long grupoId, LocalDate fechaEvento);
}
