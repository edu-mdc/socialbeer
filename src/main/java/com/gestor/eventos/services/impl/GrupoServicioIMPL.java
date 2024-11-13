package com.gestor.eventos.services.impl;


import com.gestor.eventos.dto.*;
import com.gestor.eventos.entities.*;
import com.gestor.eventos.exceptions.ResourceNotFoundException;
import com.gestor.eventos.repository.GrupoRepositorioI;
import com.gestor.eventos.repository.UsuarioRepositorioI;
import com.gestor.eventos.services.GrupoServicio;
import jakarta.transaction.Transactional;
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
public class GrupoServicioIMPL implements GrupoServicio {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GrupoRepositorioI grupoRepositorioI;

    @Autowired
    private UsuarioRepositorioI usuarioRepositorioI;

    @Override
    @Transactional
    public GrupoDTO crearGrupo(long usuarioId, GrupoDTO grupoDTO) {
        Grupo grupo = mapearEntidad(grupoDTO);
        Usuario usuario = usuarioRepositorioI.findById(usuarioId).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        grupo.setUsuario(usuario);
        Grupo nuevoGrupo = grupoRepositorioI.save(grupo);
        return mapearDTO(nuevoGrupo);
    }

    @Override
    @Transactional
    public GrupoDTO obtenerGrupoPorGrupoId(Long grupoId) {
        Grupo grupo = grupoRepositorioI.findById(grupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo", "id", grupoId));
        return mapearDTO(grupo);
    }


    @Override
    @Transactional
    public GrupoDTO obtenerGrupoPorId(Long usuarioId) {
        Grupo grupo = grupoRepositorioI.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo", "id", usuarioId));

        return mapearDTO(grupo);
    }



    @Override
    @Transactional
    public GrupoRespuesta obtenerTodosLosGrupos(int numeroDePagina, int medidaDePagina, String ordernarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordernarPor).ascending() : Sort.by(ordernarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        Page<Grupo> grupos = grupoRepositorioI.findAll(pageable);

        List<Grupo> listaDeGrupos = grupos.getContent();
        List<GrupoDTO> contenido = listaDeGrupos.stream().map(this::mapearDTO).collect(Collectors.toList());

        GrupoRespuesta grupoRespuesta = new GrupoRespuesta();
        grupoRespuesta.setContenido(contenido);
        grupoRespuesta.setNumeroPagina(grupos.getNumber());
        grupoRespuesta.setMedidaPagina(grupos.getSize());
        grupoRespuesta.setTotalElementos(grupos.getTotalElements());
        grupoRespuesta.setTotalPaginas(grupos.getTotalPages());
        grupoRespuesta.setUltima(grupos.isLast());
        return grupoRespuesta;
    }

    @Override
    @Transactional
    public GrupoDTO actualizarGrupo(Long usuarioId, GrupoDTO solicitudDeGrupo) {
        Grupo grupo = grupoRepositorioI.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo", "id", usuarioId));

        grupo.setGrupo(solicitudDeGrupo.getGrupo());
        grupo.setEstilo(solicitudDeGrupo.getEstilo());
        grupo.setTarifa(solicitudDeGrupo.getTarifa());
        grupo.setDuracion(solicitudDeGrupo.getDuracion());
        grupo.setDesplazamiento(solicitudDeGrupo.getDesplazamiento());
        grupo.setTelefono(solicitudDeGrupo.getTelefono());
        grupo.setPoblacion(solicitudDeGrupo.getPoblacion());
        grupo.setProvincia(solicitudDeGrupo.getProvincia());

        Grupo grupoActualizado = grupoRepositorioI.save(grupo);
        return mapearDTO(grupoActualizado);
    }

    @Override
    public GrupoDTO crearGrupoNuevoPorUsuarioId(Long usuarioId) {
        // Buscar al usuario por su ID
        Usuario usuario = usuarioRepositorioI.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        // Crear un grupo nuevo y asociarlo al usuario
        Grupo nuevoGrupo = new Grupo();
        nuevoGrupo.setUsuario(usuario);


        // Puedes añadir valores por defecto



        nuevoGrupo.setProvincia("");
        nuevoGrupo.setPoblacion("");

        // Guardar el nuevo cliente en la base de datos
        Grupo grupoGuardado = grupoRepositorioI.save(nuevoGrupo);

        // Retornar el cliente guardado como DTO
        return mapearDTO(grupoGuardado);
    }

    @Override
    public void eliminarGrupo(Long grupoId) {
        Grupo grupo = grupoRepositorioI.findById(grupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", grupoId));
        grupoRepositorioI.delete(grupo);
    }

    private GrupoDTO mapearDTO(Grupo grupo) {
        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setId(grupo.getId());
        grupoDTO.setGrupo(grupo.getGrupo());
        grupoDTO.setEstilo(grupo.getEstilo());
        grupoDTO.setProvincia(grupo.getProvincia());
        grupoDTO.setPoblacion(grupo.getPoblacion());
        grupoDTO.setTarifa(grupo.getTarifa());
        grupoDTO.setDuracion(grupo.getDuracion());
        grupoDTO.setDesplazamiento(grupo.getDesplazamiento());
        grupoDTO.setTelefono(grupo.getTelefono());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(grupo.getUsuario().getId());
        usuarioDTO.setNombreUsuario(grupo.getUsuario().getNombreUsuario());
        usuarioDTO.setPassword(grupo.getUsuario().getPassword());
        usuarioDTO.setEmail(grupo.getUsuario().getEmail());
        usuarioDTO.setRol(grupo.getUsuario().getRol());
        grupoDTO.setUsuario(usuarioDTO);

        Set<ValoracionDeGrupoDTO> valoraciones = grupo.getValoracionGrupos().stream()
                .map(this::convertirAValoracionDeGrupoDTO)
                .collect(Collectors.toSet());
        grupoDTO.setValoracionDeGrupo(valoraciones);

        Set<EventoDTO> eventos = grupo.getEventos().stream()
                .map(this::convertirAEventoDTO)
                .collect(Collectors.toSet());
        grupoDTO.setEventos(eventos);

        return grupoDTO;

    }

    private ValoracionDeGrupoDTO convertirAValoracionDeGrupoDTO(ValoracionDeGrupo valoracionGrupo) {
        ValoracionDeGrupoDTO dto = new ValoracionDeGrupoDTO();
        dto.setId(valoracionGrupo.getId());
        dto.setPuntuacion(valoracionGrupo.getPuntuacion());
        dto.setComentario(valoracionGrupo.getComentario());
        dto.setFechaValoracion(valoracionGrupo.getFechaValoracion());
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

    private Grupo mapearEntidad(GrupoDTO grupoDTO) {
        Grupo grupo = modelMapper.map(grupoDTO, Grupo.class);

        return grupo;
    }

    private ValoracionDeGrupoDTO mapearValoracionDeGrupoDTOExcluyendoReferencias(ValoracionDeGrupo valoracionDeGrupo) {
        ValoracionDeGrupoDTO valoracionDTO = modelMapper.map(valoracionDeGrupo, ValoracionDeGrupoDTO.class);
        // Excluir referencias tanto al grupo como al cliente
        return valoracionDTO;
    }
}
