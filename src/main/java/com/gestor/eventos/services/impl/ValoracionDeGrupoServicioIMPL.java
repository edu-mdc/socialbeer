package com.gestor.eventos.services.impl;


import com.gestor.eventos.dto.ValoracionDeGrupoDTO;
import com.gestor.eventos.entities.Cliente;
import com.gestor.eventos.entities.Grupo;
import com.gestor.eventos.entities.ValoracionDeGrupo;
import com.gestor.eventos.exceptions.GestorAppException;
import com.gestor.eventos.exceptions.ResourceNotFoundException;
import com.gestor.eventos.repository.ClienteRepositorioI;
import com.gestor.eventos.repository.GrupoRepositorioI;
import com.gestor.eventos.repository.ValoracionDeGrupoRepositorioI;
import com.gestor.eventos.services.ValoracionDeGrupoServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValoracionDeGrupoServicioIMPL implements ValoracionDeGrupoServicio {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ValoracionDeGrupoRepositorioI valoracionDeGrupoRepositorioI;

    @Autowired
    private GrupoRepositorioI grupoRepositorioI;

    @Autowired
    private ClienteRepositorioI clienteRepositorioI;

    @Override
    public ValoracionDeGrupoDTO crearValoracionDeGrupo(long grupoId, long clienteId, ValoracionDeGrupoDTO valoracionDeGrupoDTO) {
        ValoracionDeGrupo valoracionDeGrupo = mapearEntidad(valoracionDeGrupoDTO);
        Grupo grupo = grupoRepositorioI.findById(grupoId).orElseThrow(() -> new ResourceNotFoundException("Grupo", "id", grupoId));
        Cliente cliente = clienteRepositorioI.findById(clienteId).orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", clienteId));

        valoracionDeGrupo.setGrupo(grupo);
        valoracionDeGrupo.setCliente(cliente);
        ValoracionDeGrupo nuevaValoracionDeGrupo = valoracionDeGrupoRepositorioI.save(valoracionDeGrupo);
        return mapearDTO(nuevaValoracionDeGrupo);
    }

    @Override
    public List<ValoracionDeGrupoDTO> obtenerValoracionDeGrupoPorGrupoId(long grupoId) {
        List<ValoracionDeGrupo> valoracionDeGrupos = valoracionDeGrupoRepositorioI.findByGrupoId(grupoId);

        return valoracionDeGrupos.stream().map(valoracionDeGrupo -> mapearDTO(valoracionDeGrupo)).collect(Collectors.toList());
    }

    @Override
    public ValoracionDeGrupoDTO obtenerValoracionDeGrupoPorID(Long grupoId, Long valoracionDeGrupoId) {
        Grupo grupo = grupoRepositorioI.findById(grupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo", "id", grupoId));

        ValoracionDeGrupo valoracionDeGrupo = valoracionDeGrupoRepositorioI.findById(valoracionDeGrupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Valoracion", "id", valoracionDeGrupoId));

        if (!valoracionDeGrupo.getGrupo().getId().equals(grupo.getId())) {
            throw new GestorAppException(HttpStatus.BAD_REQUEST, "La valoracion no pertenece al grupo");
        }

        return mapearDTO(valoracionDeGrupo);
    }

    @Override
    public ValoracionDeGrupoDTO actualizarValoracionDeGrupo(Long grupoId, Long valoracionDeGrupoId, ValoracionDeGrupoDTO solicitudDeValoracionDeGrupo) {
        Grupo grupo = grupoRepositorioI.findById(grupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo", "id", grupoId));

        ValoracionDeGrupo valoracionDeGrupo = valoracionDeGrupoRepositorioI.findById(valoracionDeGrupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Valoracion", "id", valoracionDeGrupoId));

        if (!valoracionDeGrupo.getGrupo().getId().equals(grupo.getId())) {
            throw new GestorAppException(HttpStatus.BAD_REQUEST, "La valoracion no pertenece al grupo");
        }

        valoracionDeGrupo.setPuntuacion(solicitudDeValoracionDeGrupo.getPuntuacion());
        valoracionDeGrupo.setComentario(solicitudDeValoracionDeGrupo.getComentario());
        valoracionDeGrupo.setFechaValoracion(solicitudDeValoracionDeGrupo.getFechaValoracion());

        ValoracionDeGrupo valoracionDeGrupoActualizada = valoracionDeGrupoRepositorioI.save(valoracionDeGrupo);
        return mapearDTO(valoracionDeGrupoActualizada);
    }

    @Override
    public void eliminarValoracionDeGrupo(Long grupoId, Long valoracionDeGrupoId) {
        Grupo grupo = grupoRepositorioI.findById(grupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo", "id", grupoId));
        ValoracionDeGrupo valoracionDeGrupo = valoracionDeGrupoRepositorioI.findById(valoracionDeGrupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Valoracion", "id", valoracionDeGrupoId));
        if (!valoracionDeGrupo.getGrupo().getId().equals(grupo.getId())) {
            throw new GestorAppException(HttpStatus.BAD_REQUEST, "La valoracion no pertenece al grupo");
        }
        valoracionDeGrupoRepositorioI.delete(valoracionDeGrupo);
    }


    private ValoracionDeGrupoDTO mapearDTO(ValoracionDeGrupo valoracionDeGrupo) {
        ValoracionDeGrupoDTO valoracionDeGrupoDTO = modelMapper.map(valoracionDeGrupo, ValoracionDeGrupoDTO.class);

        return valoracionDeGrupoDTO;
    }

    private ValoracionDeGrupo mapearEntidad(ValoracionDeGrupoDTO valoracionDeGrupoDTO) {
        ValoracionDeGrupo valoracionDeGrupo = modelMapper.map(valoracionDeGrupoDTO, ValoracionDeGrupo.class);

        return valoracionDeGrupo;
    }


    private ValoracionDeGrupoDTO mapearValoracionDeGrupoDTOExcluyendoReferencias(ValoracionDeGrupo valoracionDeGrupo) {
        ValoracionDeGrupoDTO valoracionDTO = modelMapper.map(valoracionDeGrupo, ValoracionDeGrupoDTO.class);
        // Excluir referencias tanto al grupo como al cliente
        return valoracionDTO;
    }
}
