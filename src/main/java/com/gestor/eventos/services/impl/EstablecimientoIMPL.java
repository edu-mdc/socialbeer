package com.gestor.eventos.services.impl;

import com.gestor.eventos.dto.*;
import com.gestor.eventos.entities.*;
import com.gestor.eventos.exceptions.ResourceNotFoundException;
import com.gestor.eventos.repository.EstablecimientoRepositorioI;
import com.gestor.eventos.repository.UsuarioRepositorioI;
import com.gestor.eventos.services.EstablecimientoServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EstablecimientoIMPL implements EstablecimientoServicio {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EstablecimientoRepositorioI establecimientoRepositorioI;

    @Autowired
    private UsuarioRepositorioI usuarioRepositorioI;

    @Override
    public EstablecimientoDTO crearEstablecimiento(long usuarioId, EstablecimientoDTO establecimientoDTO) {
        Establecimiento establecimiento = mapearEntidad(establecimientoDTO);
        Usuario usuario = usuarioRepositorioI.findById(usuarioId).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        establecimiento.setUsuario(usuario);
        Establecimiento nuevoEstablecimiento = establecimientoRepositorioI.save(establecimiento);
        return mapearDTO(nuevoEstablecimiento);
    }

    @Override
    public EstablecimientoDTO obtenerEstablecimientoPorId(Long establecimientoId) {
        Establecimiento establecimiento = establecimientoRepositorioI.findById(establecimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento", "id", establecimientoId));

        return mapearDTO(establecimiento);
    }

    @Override
    public EstablecimientoRespuesta obtenerTodosLosEstablecimientos(int numeroDePagina, int medidaDePagina, String ordernarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordernarPor).ascending() : Sort.by(ordernarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        Page<Establecimiento> establecimientos = establecimientoRepositorioI.findAll(pageable);

        List<Establecimiento> listaDeEstablecimientos = establecimientos.getContent();
        List<EstablecimientoDTO> contenido = listaDeEstablecimientos.stream().map(this::mapearDTO).collect(Collectors.toList());

        EstablecimientoRespuesta establecimientoRespuesta = new EstablecimientoRespuesta();
        establecimientoRespuesta.setContenido(contenido);
        establecimientoRespuesta.setNumeroPagina(establecimientos.getNumber());
        establecimientoRespuesta.setMedidaPagina(establecimientos.getSize());
        establecimientoRespuesta.setTotalElementos(establecimientos.getTotalElements());
        establecimientoRespuesta.setTotalPaginas(establecimientos.getTotalPages());
        establecimientoRespuesta.setUltima(establecimientos.isLast());

        return establecimientoRespuesta;
    }

    @Override
    public EstablecimientoDTO actualizarEstablecimiento(Long establecimientoId, EstablecimientoDTO solicitudDeEstablecimiento) {
        Establecimiento establecimiento = establecimientoRepositorioI.findById(establecimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento", "id", establecimientoId));

        establecimiento.setEstablecimiento(solicitudDeEstablecimiento.getEstablecimiento());
        establecimiento.setCif(solicitudDeEstablecimiento.getCif());
        establecimiento.setAforo(solicitudDeEstablecimiento.getAforo());
        establecimiento.setDireccion(solicitudDeEstablecimiento.getDireccion());
        establecimiento.setTelefono(solicitudDeEstablecimiento.getTelefono());
        establecimiento.setPoblacion(solicitudDeEstablecimiento.getPoblacion());
        establecimiento.setProvincia(solicitudDeEstablecimiento.getProvincia());

        Establecimiento establecimientoActualizado = establecimientoRepositorioI.save(establecimiento);
        return mapearDTO(establecimientoActualizado);
    }

    @Override
    public void eliminarEstablecimiento(Long establecimientoId) {
        Establecimiento establecimiento = establecimientoRepositorioI.findById(establecimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento", "id", establecimientoId));
        establecimientoRepositorioI.delete(establecimiento);
    }

    private EstablecimientoDTO mapearDTO(Establecimiento establecimiento) {
        EstablecimientoDTO dto = new EstablecimientoDTO();
        dto.setId(establecimiento.getId());
        dto.setEstablecimiento(establecimiento.getEstablecimiento());
        dto.setCif(establecimiento.getCif());
        dto.setAforo(establecimiento.getAforo());
        dto.setDireccion(establecimiento.getDireccion());
        dto.setProvincia(establecimiento.getProvincia());
        dto.setPoblacion(establecimiento.getPoblacion());
        dto.setTelefono(establecimiento.getTelefono());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(establecimiento.getUsuario().getId());
        usuarioDTO.setNombreUsuario(establecimiento.getUsuario().getNombreUsuario());
        usuarioDTO.setPassword(establecimiento.getUsuario().getPassword());
        usuarioDTO.setEmail(establecimiento.getUsuario().getEmail());
        usuarioDTO.setRol(establecimiento.getUsuario().getRol());
        dto.setUsuario(usuarioDTO);

        Set<ValoracionDeEstablecimientoDTO> valoraciones = establecimiento.getValoracionEstablecimientos().stream()
                .map(this::convertirAValoracionDeEstablecimientoDTO)
                .collect(Collectors.toSet());
        dto.setValoracionDeEstablecimiento(valoraciones);

        Set<EventoDTO> eventos = establecimiento.getEventos().stream()
                .map(this::convertirAEventoDTO)
                .collect(Collectors.toSet());
        dto.setEventos(eventos);

        return dto;
    }
    private ValoracionDeEstablecimientoDTO convertirAValoracionDeEstablecimientoDTO(ValoracionDeEstablecimiento valoracion) {
        ValoracionDeEstablecimientoDTO dto = new ValoracionDeEstablecimientoDTO();
        dto.setId(valoracion.getId());
        dto.setPuntuacion(valoracion.getPuntuacion());
        dto.setComentario(valoracion.getComentario());
        dto.setFechaValoracion(valoracion.getFechaValoracion());
        return dto;
    }

    private EventoDTO convertirAEventoDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setFechaContratacion(evento.getFechaContratacion());
        dto.setFechaEvento(evento.getFechaEvento());
        dto.setHoraEvento(evento.getHoraEvento());
        dto.setPrecio(evento.getPrecio());
        dto.setEstado(evento.getEstado());
        return dto;
    }

    private Establecimiento mapearEntidad(EstablecimientoDTO establecimientoDTO) {
        Establecimiento establecimiento = modelMapper.map(establecimientoDTO, Establecimiento.class);

        return establecimiento;
    }

    private ValoracionDeEstablecimientoDTO mapearValoracionEstablecimientoDTOExcluyendoReferencias(ValoracionDeEstablecimiento valoracionDeEstablecimiento) {
        ValoracionDeEstablecimientoDTO valoracionDTO = modelMapper.map(valoracionDeEstablecimiento, ValoracionDeEstablecimientoDTO.class);
        // Excluir referencias tanto al grupo como al cliente
        return valoracionDTO;
    }
}
