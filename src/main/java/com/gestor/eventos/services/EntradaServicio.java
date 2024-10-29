package com.gestor.eventos.services;

import com.gestor.eventos.dto.EntradaDTO;

import java.util.List;

public interface EntradaServicio {

    public EntradaDTO crearEntrada(long eventoId, long clienteId, EntradaDTO entradaDTO);

    public List<EntradaDTO> obtenerEntradasPorEventoId(long eventoId);

    public List<EntradaDTO> obtenerEntradasPorClienteId(long clienteId);

    public List<EntradaDTO> obtenerEntradasPorClienteIdDeUnEventoId(long clienteId, long eventoId);

    public EntradaDTO obtenerEntradaPorID(Long eventoId, Long entradaId);

    public void eliminarEntrada(Long eventoId, Long entradaId);
}
