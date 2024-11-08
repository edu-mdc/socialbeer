package com.gestor.eventos.services;

import com.gestor.eventos.dto.ClienteDTO;
import com.gestor.eventos.dto.EstablecimientoDTO;
import com.gestor.eventos.dto.EstablecimientoRespuesta;

public interface EstablecimientoServicio {

    public EstablecimientoDTO crearEstablecimiento(long usuarioId, EstablecimientoDTO establecimientoDTO);

    public EstablecimientoDTO crearEstablecimientoNuevoPorUsuarioId(Long usuarioId);

    public EstablecimientoDTO obtenerEstablecimientoPorId (Long establecimientoId);

    EstablecimientoRespuesta obtenerTodosLosEstablecimientos(int numeroDePagina, int medidaDePagina, String ordernarPor, String sortDir);

    public EstablecimientoDTO actualizarEstablecimiento(Long establecimientoId, EstablecimientoDTO solicitudDeEstablecimiento);

    public void eliminarEstablecimiento(Long establecimientoId);
}
