package com.gestor.eventos.services;

import com.gestor.eventos.dto.ValoracionDeGrupoDTO;

import java.util.List;

public interface ValoracionDeGrupoServicio {

    public ValoracionDeGrupoDTO crearValoracionDeGrupo(long grupoId, long clienteId,ValoracionDeGrupoDTO valoracionDeGrupoDTO);

    public List<ValoracionDeGrupoDTO> obtenerValoracionDeGrupoPorGrupoId(long grupoId);

    public ValoracionDeGrupoDTO obtenerValoracionDeGrupoPorID(Long grupoId, Long valoracionDeGrupoId);

    public ValoracionDeGrupoDTO actualizarValoracionDeGrupo(Long grupoId,Long valoracionDeGrupoId,ValoracionDeGrupoDTO solicitudDeValoracionDeGrupo);

    public void eliminarValoracionDeGrupo(Long grupoId, Long valoracionDeGrupoId);
}
