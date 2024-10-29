package com.gestor.eventos.services;

import com.gestor.eventos.dto.ValoracionDeEstablecimientoDTO;

import java.util.List;

public interface ValoracionDeEstablecimientoServicio {

    public ValoracionDeEstablecimientoDTO crearValoracionDeEstablecimiento(long establecimientoId, long clienteId, ValoracionDeEstablecimientoDTO valoracionDeEstablecimientoDTO);

    public List<ValoracionDeEstablecimientoDTO> obtenerValoracionDeEstablecimientoPorestablecimientoId(long establecimientoId);

    public ValoracionDeEstablecimientoDTO obtenerValoracionDeEstablecimientoPorID(Long establecimientoId, Long valoracionDeEstablecimientoId);

    public ValoracionDeEstablecimientoDTO actualizarValoracionDeEstablecimiento(Long establecimientoId,Long valoracionDeEstablecimientoId,ValoracionDeEstablecimientoDTO solicitudDeValoracionDeEstablecimiento);

    public void eliminarValoracionDeEstablecimiento(Long establecimientoId, Long valoracionDeEstablecimientoId);
}
