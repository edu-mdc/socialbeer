package com.gestor.eventos.repository;

import com.gestor.eventos.entities.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstablecimientoRepositorioI extends JpaRepository<Establecimiento,Long> {
}
