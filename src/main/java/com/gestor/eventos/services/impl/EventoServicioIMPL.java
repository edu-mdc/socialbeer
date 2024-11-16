package com.gestor.eventos.services.impl;

import com.gestor.eventos.dto.*;
import com.gestor.eventos.entities.*;
import com.gestor.eventos.exceptions.GestorAppException;
import com.gestor.eventos.exceptions.ResourceConflictException;
import com.gestor.eventos.exceptions.ResourceNotFoundException;
import com.gestor.eventos.repository.EstablecimientoRepositorioI;
import com.gestor.eventos.repository.EventoRepositorioI;
import com.gestor.eventos.repository.GrupoRepositorioI;
import com.gestor.eventos.services.EventoServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoServicioIMPL implements EventoServicio {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventoRepositorioI eventoRepositorioI;

    @Autowired
    private EstablecimientoRepositorioI establecimientoRepositorioI;

    @Autowired
    private GrupoRepositorioI grupoRepositorioI;


    @Override
    public EventoDTO crearEvento(long establecimientoId, long grupoId, EventoDTO eventoDTO) {
        // Convertimos la fecha de evento a LocalDate
        LocalDate fechaEvento = eventoDTO.getFechaEvento(); // Usamos LocalDate directamente

        // Verificamos si ya existe un evento del grupo en la misma fecha
        List<Evento> eventosExistentes = eventoRepositorioI.findByGrupoIdAndFechaEvento(grupoId, fechaEvento);
        if (!eventosExistentes.isEmpty()) {
            throw new ResourceConflictException("El grupo ya tiene un evento programado para la fecha: " + fechaEvento);
        }

        List<Evento> eventosEstablecimiento = eventoRepositorioI.findByEstablecimientoIdAndFechaEvento(establecimientoId, fechaEvento);
        if (!eventosEstablecimiento.isEmpty()) {
            throw new ResourceConflictException("El establecimiento ya tiene un evento programado para la fecha: " + fechaEvento);
        }

        // Mapeamos el DTO a entidad
        Evento evento = mapearEntidad(eventoDTO);

        // Buscamos el establecimiento y el grupo
        Establecimiento establecimiento = establecimientoRepositorioI.findById(establecimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento", "id", establecimientoId));
        Grupo grupo = grupoRepositorioI.findById(grupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo", "id", grupoId));

        // Establecemos el establecimiento y el grupo en el evento
        evento.setEstablecimiento(establecimiento);
        evento.setGrupo(grupo);

        // Guardamos el evento
        Evento nuevoEvento = eventoRepositorioI.save(evento);
        return mapearDTO(nuevoEvento);
    }

    @Override
    public EventoDTO obtenerEventoPorId(Long eventoId) {
        Evento evento = eventoRepositorioI.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", "id", eventoId));
        return mapearDTO(evento);
    }

    @Override
    public List<EventoDTO> obtenerEventosPorEstablecimientoId(long establecimientoId) {
        List<Evento> eventos = eventoRepositorioI.findByEstablecimientoId(establecimientoId);

        return eventos.stream().map(evento -> mapearDTO(evento)).collect(Collectors.toList());
    }

    @Override
    public List<EventoDTO> obtenerEventosPorGrupoId(long grupoId) {
        List<Evento> eventos = eventoRepositorioI.findByGrupoId(grupoId);

        return eventos.stream().map(evento -> mapearDTO(evento)).collect(Collectors.toList());
    }

    @Override
    public EventoRespuesta obtenerTodosEventos(int numeroDePagina, int medidaDePagina, String ordernarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordernarPor).ascending() : Sort.by(ordernarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        Page<Evento> eventos = eventoRepositorioI.findAll(pageable);

        List<Evento> listaDeEventos = eventos.getContent();
        List<EventoDTO> contenido = listaDeEventos.stream().map(this::mapearDTO).collect(Collectors.toList());

        EventoRespuesta eventoRespuesta = new EventoRespuesta();
        eventoRespuesta.setContenido(contenido);
        eventoRespuesta.setNumeroPagina(eventos.getNumber());
        eventoRespuesta.setMedidaPagina(eventos.getSize());
        eventoRespuesta.setTotalElementos(eventos.getTotalElements());
        eventoRespuesta.setTotalPaginas(eventos.getTotalPages());
        eventoRespuesta.setUltima(eventos.isLast());
        return eventoRespuesta;
    }

    public List<EventoDTO> obtenerTodosLosEventos() {
        List<Evento> eventos = eventoRepositorioI.findAll();
        return eventos.stream().map(this::mapearDTO).collect(Collectors.toList());
    }

    @Override
    public EventoDTO actualizarEvento(Long eventoId, EventoDTO solicitudDeEvento) {
        Evento evento = eventoRepositorioI.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", "id", eventoId));

        Long grupoId = evento.getGrupo().getId();
        Long establecimientoId = evento.getEstablecimiento().getId();
        LocalDate fechaEvento = solicitudDeEvento.getFechaEvento();

        List<Evento> eventosEstablecimiento = eventoRepositorioI.findByEstablecimientoIdAndFechaEvento(establecimientoId, fechaEvento);
        List<Evento> eventosGrupo = eventoRepositorioI.findByGrupoIdAndFechaEvento(grupoId, fechaEvento);

        eventosEstablecimiento.removeIf(e -> e.getId().equals(eventoId));
        eventosGrupo.removeIf(e -> e.getId().equals(eventoId));

        if (!eventosEstablecimiento.isEmpty() || !eventosGrupo.isEmpty()) {
            throw new ResourceConflictException("El grupo o el establecimiento ya tienen un evento en esta fecha.");
        }

        evento.setFechaContratacion(solicitudDeEvento.getFechaContratacion());
        evento.setFechaEvento(solicitudDeEvento.getFechaEvento());
        evento.setHoraEvento(solicitudDeEvento.getHoraEvento());
        evento.setPrecio(solicitudDeEvento.getPrecio());
        evento.setVentas(solicitudDeEvento.getVentas());
        evento.setEstado(solicitudDeEvento.getEstado());


        Evento eventoActualizado = eventoRepositorioI.save(evento);
        return mapearDTO(eventoActualizado);
    }

    @Override
    public void eliminarEvento(Long eventoId) {
        Evento evento = eventoRepositorioI.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", "id", eventoId));

        eventoRepositorioI.delete(evento);
    }


    private EventoDTO mapearDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setFechaContratacion(evento.getFechaContratacion());
        dto.setFechaEvento(evento.getFechaEvento());
        dto.setHoraEvento(evento.getHoraEvento());
        dto.setPrecio(evento.getPrecio());
        dto.setVentas(evento.getVentas());
        dto.setEstado(evento.getEstado());
        dto.setNombreGrupo(evento.getGrupo().getGrupo());
        dto.setNombreEstablecimiento(evento.getEstablecimiento().getEstablecimiento());
        if (evento.getGrupo() != null) {
            GrupoDTO grupoDTO = convertirAGrupoDTO(evento.getGrupo());
            dto.setGrupo(grupoDTO);
        }

        if (evento.getEstablecimiento() != null) { // Añadir esta condición
            EstablecimientoDTO establecimientoDTO = convertirAEstablecimientoDTO(evento.getEstablecimiento());
            dto.setEstablecimiento(establecimientoDTO);
        }

        // Si es necesario, añadir conversión para entradas

        return dto;
    }

    private GrupoDTO convertirAGrupoDTO(Grupo grupo) {
        GrupoDTO dto = new GrupoDTO();
        dto.setId(grupo.getId());
        dto.setGrupo(grupo.getGrupo());
        dto.setEstilo(grupo.getEstilo());
        dto.setProvincia(grupo.getProvincia());
        dto.setPoblacion(grupo.getPoblacion());
        dto.setTarifa(grupo.getTarifa());
        dto.setDuracion(grupo.getDuracion());
        dto.setDesplazamiento(grupo.getDesplazamiento());
        dto.setTelefono(grupo.getTelefono());
        return dto;
    }

    public boolean verificarDisponibilidad(Long establecimientoId,Long grupoId, LocalDate fechaEvento) {
        List<Evento> eventosEstablecimiento = eventoRepositorioI.findByEstablecimientoIdAndFechaEvento(establecimientoId, fechaEvento);
        List<Evento> eventosGrupo = eventoRepositorioI.findByGrupoIdAndFechaEvento(grupoId, fechaEvento);

        return eventosGrupo.isEmpty() && eventosEstablecimiento.isEmpty();
    }

    private EstablecimientoDTO convertirAEstablecimientoDTO(Establecimiento establecimiento) { // Añadir este método
        EstablecimientoDTO dto = new EstablecimientoDTO();
        dto.setId(establecimiento.getId());
        dto.setEstablecimiento(establecimiento.getEstablecimiento());
        dto.setCif(establecimiento.getCif());
        dto.setAforo(establecimiento.getAforo());
        dto.setDireccion(establecimiento.getDireccion());
        dto.setProvincia(establecimiento.getProvincia());
        dto.setPoblacion(establecimiento.getPoblacion());
        dto.setTelefono(establecimiento.getTelefono());
        return dto;
    }

    private Evento mapearEntidad(EventoDTO eventoDTO) {
        Evento evento = modelMapper.map(eventoDTO, Evento.class);

        return evento;
    }
}
