package com.gestor.eventos.repository;

import com.gestor.eventos.entities.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntradaRepositorioI extends JpaRepository<Entrada,Long> {

    public List<Entrada> findByEventoId(long eventoId);

    public List<Entrada> findByClienteId(long eventoId);
}
