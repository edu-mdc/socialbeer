package com.gestor.eventos.services;

import com.gestor.eventos.dto.EventoDTO;
import com.gestor.eventos.dto.EventoRespuesta;

import java.time.LocalDate;
import java.util.List;

public interface EventoServicio {

    public EventoDTO crearEvento(long establecimientoId, long grupoId, EventoDTO eventoDTO);

    public EventoDTO obtenerEventoPorId(Long eventoId);

    public List<EventoDTO> obtenerEventosPorEstablecimientoId(long establecimientoId);

    public List<EventoDTO> obtenerEventosPorGrupoId(long grupoId);

    EventoRespuesta obtenerTodosEventos(int numeroDePagina, int medidaDePagina, String ordernarPor, String sortDir);

    List<EventoDTO> obtenerTodosLosEventos();

    public EventoDTO actualizarEvento(Long eventoId,EventoDTO solicitudDeEvento);

    public void eliminarEvento(Long eventoId);

    public boolean verificarDisponibilidad(Long establecimientoId,Long grupoId, LocalDate fechaEvento);
}
