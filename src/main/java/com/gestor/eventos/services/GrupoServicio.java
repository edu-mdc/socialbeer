package com.gestor.eventos.services;

import com.gestor.eventos.dto.GrupoDTO;
import com.gestor.eventos.dto.GrupoRespuesta;

public interface GrupoServicio {
    public GrupoDTO crearGrupo(long usuarioId, GrupoDTO grupoDTO);

    public GrupoDTO obtenerGrupoPorId (Long grupoId);

    GrupoRespuesta obtenerTodosLosGrupos(int numeroDePagina, int medidaDePagina, String ordernarPor, String sortDir);

    public GrupoDTO actualizarGrupo(Long grupoId, GrupoDTO solicitudDeGrupo);

    public void eliminarGrupo(Long grupoId);
}
