package com.gestor.eventos.services.impl;

import com.gestor.eventos.dto.EntradaDTO;
import com.gestor.eventos.entities.*;
import com.gestor.eventos.exceptions.GestorAppException;
import com.gestor.eventos.exceptions.ResourceNotFoundException;
import com.gestor.eventos.repository.ClienteRepositorioI;
import com.gestor.eventos.repository.EntradaRepositorioI;
import com.gestor.eventos.repository.EventoRepositorioI;
import com.gestor.eventos.services.EntradaServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntradaServicioIMPL implements EntradaServicio {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntradaRepositorioI entradaRepositorioI;

    @Autowired
    private ClienteRepositorioI clienteRepositorioI;

    @Autowired
    private EventoRepositorioI eventoRepositorioI;

    @Override
    public EntradaDTO crearEntrada(long eventoId, long clienteId, EntradaDTO entradaDTO) {
        Entrada entrada = mapearEntidad(entradaDTO);
        Evento evento = eventoRepositorioI.findById(eventoId).orElseThrow(() -> new ResourceNotFoundException("Evento", "id", eventoId));
        Cliente cliente = clienteRepositorioI.findById(clienteId).orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", clienteId));

        entrada.setEvento(evento);
        entrada.setCliente(cliente);
        Entrada nuevaEntrada = entradaRepositorioI.save(entrada);
        return mapearDTO(nuevaEntrada);
    }

    @Override
    public List<EntradaDTO> obtenerEntradasPorEventoId(long eventoId) {
        List<Entrada> entradas = entradaRepositorioI.findByEventoId(eventoId);

        return entradas.stream().map(entrada -> mapearDTO(entrada)).collect(Collectors.toList());
    }

    @Override
    public List<EntradaDTO> obtenerEntradasPorClienteId(long clienteId) {
        List<Entrada> entradas = entradaRepositorioI.findByClienteId(clienteId);

        return entradas.stream().map(entrada -> mapearDTO(entrada)).collect(Collectors.toList());
    }

    @Override
    public List<EntradaDTO> obtenerEntradasPorClienteIdDeUnEventoId(long clienteId, long eventoId) {
        // Obtener todas las entradas del cliente
        List<Entrada> entradas = entradaRepositorioI.findByClienteId(clienteId);

        // Obtener el evento
        Evento evento = eventoRepositorioI.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", "id", eventoId));

        // Filtrar las entradas del cliente para el evento específico
        List<Entrada> entradasFiltradas = entradas.stream()
                .filter(entrada -> entrada.getEvento().getId() == eventoId)
                .collect(Collectors.toList());

        // Mapear las entidades Entrada a DTOs
        return entradasFiltradas.stream()
                .map(this::mapearDTO)
                .collect(Collectors.toList());

    }

    @Override
    public EntradaDTO obtenerEntradaPorID(Long eventoId, Long entradaId) {
        Evento evento = eventoRepositorioI.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", "id", eventoId));

        Entrada entrada = entradaRepositorioI.findById(entradaId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrada", "id", entradaId));

        if (!entrada.getEvento().getId().equals(evento.getId())) {
            throw new GestorAppException(HttpStatus.BAD_REQUEST, "La entrada no pertenece al evento");
        }

        return mapearDTO(entrada);
    }

    @Override
    public void eliminarEntrada(Long eventoId, Long entradaId) {
        Evento evento = eventoRepositorioI.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", "id", eventoId));

        Entrada entrada = entradaRepositorioI.findById(entradaId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrada", "id", entradaId));

        if (!entrada.getEvento().getId().equals(evento.getId())) {
            throw new GestorAppException(HttpStatus.BAD_REQUEST, "La entrada no pertenece al evento");
        }

        entradaRepositorioI.delete(entrada);
    }

    private EntradaDTO mapearDTO(Entrada entrada) {
        EntradaDTO entradaDTO = modelMapper.map(entrada, EntradaDTO.class);

        return entradaDTO;
    }

    private Entrada mapearEntidad(EntradaDTO entradaDTO) {
        Entrada entrada = modelMapper.map(entradaDTO, Entrada.class);

        return entrada;
    }

}
