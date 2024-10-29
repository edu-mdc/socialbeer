package com.gestor.eventos.services;

import com.gestor.eventos.dto.EventoDTO;
import com.gestor.eventos.dto.EventoRespuesta;

import java.util.List;

public interface EventoServicio {

    public EventoDTO crearEvento(long establecimientoId, long grupoId, EventoDTO eventoDTO);

    public List<EventoDTO> obtenerEventosPorEstablecimientoId(long establecimientoId);

    public List<EventoDTO> obtenerEventosPorGrupoId(long grupoId);

    EventoRespuesta obtenerTodosEventos(int numeroDePagina, int medidaDePagina, String ordernarPor, String sortDir);

    public EventoDTO actualizarEvento(Long establecimientoId,Long eventoId,EventoDTO solicitudDeEvento);

    public void eliminarEvento(Long establecimientoId, Long eventoId);
}
