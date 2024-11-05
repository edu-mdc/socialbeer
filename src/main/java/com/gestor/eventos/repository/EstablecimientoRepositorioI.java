package com.gestor.eventos.repository;

import com.gestor.eventos.entities.Cliente;
import com.gestor.eventos.entities.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstablecimientoRepositorioI extends JpaRepository<Establecimiento,Long> {
    Optional<Establecimiento> findByUsuarioId(Long usuarioId);
}
