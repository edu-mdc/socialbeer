package com.gestor.eventos.services;

import com.gestor.eventos.dto.UsuarioDTO;
import com.gestor.eventos.dto.UsuarioRespuesta;

public interface UsuarioServicio {

    UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO);

    UsuarioRespuesta obtenerTodasLosUsuarios(int numeroDePagina, int medidaDePagina, String ordernarPor, String sortDir);

    UsuarioDTO obtenerUsuariosPorId(Long id);

    UsuarioDTO actualizarUsuario(UsuarioDTO usuarioDTO, Long id);

    void eliminarUsuario(Long id);
}
