package com.gestor.eventos.services.impl;

import com.gestor.eventos.dto.ValoracionDeEstablecimientoDTO;
import com.gestor.eventos.entities.*;
import com.gestor.eventos.exceptions.GestorAppException;
import com.gestor.eventos.exceptions.ResourceNotFoundException;
import com.gestor.eventos.repository.ClienteRepositorioI;
import com.gestor.eventos.repository.EstablecimientoRepositorioI;
import com.gestor.eventos.repository.ValoracionDeEstablecimientoRepositorioI;
import com.gestor.eventos.services.ValoracionDeEstablecimientoServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValoracionDeEstablecimientoIMPL implements ValoracionDeEstablecimientoServicio {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ValoracionDeEstablecimientoRepositorioI valoracionDeEstablecimientoRepositorioI;

    @Autowired
    private EstablecimientoRepositorioI establecimientoRepositorioI;

    @Autowired
    private ClienteRepositorioI clienteRepositorioI;


    @Override
    public ValoracionDeEstablecimientoDTO crearValoracionDeEstablecimiento(long establecimientoId, long clienteId, ValoracionDeEstablecimientoDTO valoracionDeEstablecimientoDTO) {
        ValoracionDeEstablecimiento valoracionDeEstablecimiento = mapearEntidad(valoracionDeEstablecimientoDTO);
        Establecimiento establecimiento = establecimientoRepositorioI.findById(establecimientoId).orElseThrow(() -> new ResourceNotFoundException("Establecimiento", "id", establecimientoId));
        Cliente cliente = clienteRepositorioI.findById(clienteId).orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", clienteId));

        valoracionDeEstablecimiento.setEstablecimiento(establecimiento);
        valoracionDeEstablecimiento.setCliente(cliente);
        ValoracionDeEstablecimiento nuevaValoracionDeEstablecimiento = valoracionDeEstablecimientoRepositorioI.save(valoracionDeEstablecimiento);
        return mapearDTO(nuevaValoracionDeEstablecimiento);
    }



    @Override
    public List<ValoracionDeEstablecimientoDTO> obtenerValoracionDeEstablecimientoPorestablecimientoId(long establecimientoId) {
        List<ValoracionDeEstablecimiento> valoracionDeEstablecimientos = valoracionDeEstablecimientoRepositorioI.findByEstablecimientoId(establecimientoId);

        return valoracionDeEstablecimientos.stream().map(valoracionDeEstablecimiento -> mapearDTO(valoracionDeEstablecimiento)).collect(Collectors.toList());
    }

    @Override
    public ValoracionDeEstablecimientoDTO obtenerValoracionDeEstablecimientoPorID(Long establecimientoId, Long valoracionDeEstablecimientoId) {
        Establecimiento establecimiento = establecimientoRepositorioI.findById(establecimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento", "id", establecimientoId));

        ValoracionDeEstablecimiento valoracionDeEstablecimiento = valoracionDeEstablecimientoRepositorioI.findById(valoracionDeEstablecimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Valoracion", "id", valoracionDeEstablecimientoId));

        if (!valoracionDeEstablecimiento.getEstablecimiento().getId().equals(establecimiento.getId())) {
            throw new GestorAppException(HttpStatus.BAD_REQUEST, "La valoracion no pertenece al grupo");
        }

        return mapearDTO(valoracionDeEstablecimiento);
    }

    @Override
    public ValoracionDeEstablecimientoDTO actualizarValoracionDeEstablecimiento(Long establecimientoId, Long valoracionDeEstablecimientoId, ValoracionDeEstablecimientoDTO solicitudDeValoracionDeEstablecimiento) {
        Establecimiento establecimiento = establecimientoRepositorioI.findById(establecimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento", "id", establecimientoId));

        ValoracionDeEstablecimiento valoracionDeEstablecimiento = valoracionDeEstablecimientoRepositorioI.findById(valoracionDeEstablecimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Valoracion", "id", valoracionDeEstablecimientoId));

        if (!valoracionDeEstablecimiento.getEstablecimiento().getId().equals(establecimiento.getId())) {
            throw new GestorAppException(HttpStatus.BAD_REQUEST, "La valoracion no pertenece al grupo");
        }

        valoracionDeEstablecimiento.setPuntuacion(solicitudDeValoracionDeEstablecimiento.getPuntuacion());
        valoracionDeEstablecimiento.setComentario(solicitudDeValoracionDeEstablecimiento.getComentario());
        valoracionDeEstablecimiento.setFechaValoracion(solicitudDeValoracionDeEstablecimiento.getFechaValoracion());

        ValoracionDeEstablecimiento valoracionDeEstablecimientoActualizada = valoracionDeEstablecimientoRepositorioI.save(valoracionDeEstablecimiento);
        return mapearDTO(valoracionDeEstablecimientoActualizada);
    }

    @Override
    public void eliminarValoracionDeEstablecimiento(Long establecimientoId, Long valoracionDeEstablecimientoId) {
        Establecimiento establecimiento = establecimientoRepositorioI.findById(establecimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento", "id", establecimientoId));
        ValoracionDeEstablecimiento valoracionDeEstablecimiento = valoracionDeEstablecimientoRepositorioI.findById(valoracionDeEstablecimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Valoracion", "id", valoracionDeEstablecimientoId));
        if (!valoracionDeEstablecimiento.getEstablecimiento().getId().equals(establecimiento.getId())) {
            throw new GestorAppException(HttpStatus.BAD_REQUEST, "La valoracion no pertenece al grupo");
        }
        valoracionDeEstablecimientoRepositorioI.delete(valoracionDeEstablecimiento);
    }

    private ValoracionDeEstablecimientoDTO mapearDTO(ValoracionDeEstablecimiento valoracionDeEstablecimiento) {
        ValoracionDeEstablecimientoDTO valoracionDeEstablecimientoDTO = modelMapper.map(valoracionDeEstablecimiento, ValoracionDeEstablecimientoDTO.class);

        return valoracionDeEstablecimientoDTO;
    }

    private ValoracionDeEstablecimiento mapearEntidad(ValoracionDeEstablecimientoDTO valoracionDeEstablecimientoDTO) {
        ValoracionDeEstablecimiento valoracionDeEstablecimiento = modelMapper.map(valoracionDeEstablecimientoDTO, ValoracionDeEstablecimiento.class);

        return valoracionDeEstablecimiento;
    }


    private ValoracionDeEstablecimientoDTO mapearValoracionDeEstablecimientoDTOExcluyendoReferencias(ValoracionDeEstablecimiento valoracionDeEstablecimiento) {
        ValoracionDeEstablecimientoDTO valoracionDTO = modelMapper.map(valoracionDeEstablecimiento, ValoracionDeEstablecimientoDTO.class);
        // Excluir referencias tanto al grupo como al cliente
        return valoracionDTO;
    }
}
