package com.gestor.eventos.repository;

import com.gestor.eventos.entities.ValoracionDeEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValoracionDeEstablecimientoRepositorioI extends JpaRepository<ValoracionDeEstablecimiento,Long> {

    public List<ValoracionDeEstablecimiento> findByEstablecimientoId(long establecimientoId);
}
