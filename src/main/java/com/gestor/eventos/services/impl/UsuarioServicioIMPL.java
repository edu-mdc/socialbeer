package com.gestor.eventos.services.impl;

import com.gestor.eventos.dto.*;
import com.gestor.eventos.entities.*;
import com.gestor.eventos.exceptions.ResourceNotFoundException;
import com.gestor.eventos.repository.ClienteRepositorioI;
import com.gestor.eventos.repository.EstablecimientoRepositorioI;
import com.gestor.eventos.repository.GrupoRepositorioI;
import com.gestor.eventos.repository.UsuarioRepositorioI;
import com.gestor.eventos.services.UsuarioServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServicioIMPL implements UsuarioServicio {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepositorioI usuarioRepositorioI;

    @Autowired
    private ClienteRepositorioI clienteRepositorioI;

    @Autowired
    private EstablecimientoRepositorioI establecimientoRepositorioI;

    @Autowired
    private GrupoRepositorioI grupoRepositorioI;

    @Autowired
    private  EstablecimientoIMPL establecimientoIMPL;

    @Autowired
    private ClienteServicioIMPL clienteServicioIMPL;

    @Autowired
    private GrupoServicioIMPL grupoServicioIMPL;

    @Override
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        // Primero mapeamos el usuario y lo guardamos para obtener su ID
        Usuario usuario = mapearEntidad(usuarioDTO);
        Usuario nuevoUsuario = usuarioRepositorioI.save(usuario);


        // Ahora, en función del rol, creamos la entidad correspondiente
        if (nuevoUsuario.getRol() == Usuario.Rol.CLIENTE) {
            clienteServicioIMPL.crearClienteNuevoPorUsuarioId(nuevoUsuario.getId());

        }
        else if (nuevoUsuario.getRol() == Usuario.Rol.ESTABLECIMIENTO) {
           establecimientoIMPL.crearEstablecimientoNuevoPorUsuarioId(nuevoUsuario.getId());
        }
        else if (nuevoUsuario.getRol()== Usuario.Rol.GRUPO) {
            grupoServicioIMPL.crearGrupoNuevoPorUsuarioId(nuevoUsuario.getId());
        }

        // Guardamos nuevamente el usuario para asegurar que la entidad asociada está ligada correctamente
        UsuarioDTO usuarioRespuesta = mapearDTO(usuarioRepositorioI.save(nuevoUsuario));

        return usuarioRespuesta;
    }

    @Override
    public UsuarioRespuesta obtenerTodasLosUsuarios(int numeroDePagina, int medidaDePagina, String ordernarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordernarPor).ascending() : Sort.by(ordernarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        Page<Usuario> usuarios = usuarioRepositorioI.findAll(pageable);

        List<Usuario> listaDeUsuarios = usuarios.getContent();
        List<UsuarioDTO> contenido = listaDeUsuarios.stream().map(this::mapearDTO).collect(Collectors.toList());

        UsuarioRespuesta usuarioRespuesta = new UsuarioRespuesta();
        usuarioRespuesta.setContenido(contenido);
        usuarioRespuesta.setNumeroPagina(usuarios.getNumber());
        usuarioRespuesta.setMedidaPagina(usuarios.getSize());
        usuarioRespuesta.setTotalElementos(usuarios.getTotalElements());
        usuarioRespuesta.setTotalPaginas(usuarios.getTotalPages());
        usuarioRespuesta.setUltima(usuarios.isLast());
        return usuarioRespuesta;
    }

    @Override
    public UsuarioDTO obtenerUsuariosPorId(Long id_usuario) {
        Usuario usuario = usuarioRepositorioI.findById(id_usuario).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id_usuario));
        if (usuario.getCliente() != null) {
            usuario.getCliente().getNombre();
        }
        if (usuario.getGrupo() != null) {
            usuario.getGrupo().getGrupo();
        }
        if (usuario.getEstablecimiento() != null) {
            usuario.getEstablecimiento().getEstablecimiento();
        }
        return mapearDTO(usuario);
    }

    public UsuarioDTO obtenerUsuarioPorEstablecimientoId(Long idEstablecimiento) {
        // Busca el establecimiento por ID
        Establecimiento establecimiento = establecimientoRepositorioI.findById(idEstablecimiento)
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento", "id", idEstablecimiento));

        // Obtiene el usuario asociado al establecimiento
        Usuario usuario = establecimiento.getUsuario();
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario", "id del establecimiento", idEstablecimiento);
        }

        // Convierte el usuario en DTO y lo devuelve
        return mapearDTO(usuario);
    }

    @Override
    public UsuarioDTO actualizarUsuario(UsuarioDTO usuarioDTO, Long id_usuario) {
        Usuario usuario = usuarioRepositorioI.findById(id_usuario).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id_usuario));
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setRol(usuarioDTO.getRol());
        usuario.setEmail(usuarioDTO.getEmail());

        if (usuarioDTO.getCliente() != null) {
            Cliente cliente = modelMapper.map(usuarioDTO.getCliente(), Cliente.class);
            cliente.setUsuario(usuario);
            usuario.setCliente(cliente);
        } else {
            usuario.setCliente(null);
        }

        if (usuarioDTO.getGrupo() != null) {
            Grupo grupo = modelMapper.map(usuarioDTO.getGrupo(), Grupo.class);
            grupo.setUsuario(usuario);
            usuario.setGrupo(grupo);
        } else {
            usuario.setGrupo(null);
        }

        if (usuarioDTO.getEstablecimiento() != null) {
            Establecimiento establecimiento = modelMapper.map(usuarioDTO.getEstablecimiento(), Establecimiento.class);
            establecimiento.setUsuario(usuario);
            usuario.setEstablecimiento(establecimiento);
        } else {
            usuario.setEstablecimiento(null);
        }

        Usuario usuarioActualizado = usuarioRepositorioI.save(usuario);
        return mapearDTO(usuarioActualizado);
    }

    @Override
    public void eliminarUsuario(Long id_usuario) {
        Usuario usuario = usuarioRepositorioI.findById(id_usuario).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id_usuario));
        usuarioRepositorioI.delete(usuario);
    }


    //Convierte Entidad a DTO
    private UsuarioDTO mapearDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        return usuarioDTO;
    }

    //Convierte de DTO a Entidad
    private Usuario mapearEntidad(UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        return usuario;
    }

    private ValoracionDeGrupoDTO mapearValoracionDeGrupoDTOExcluyendoReferencias(ValoracionDeGrupo valoracionDeGrupo) {
        ValoracionDeGrupoDTO valoracionDTO = modelMapper.map(valoracionDeGrupo, ValoracionDeGrupoDTO.class);
        // Excluir referencias tanto al grupo como al cliente
        valoracionDTO.setGrupo(null);  // Evita la referencia circular
        valoracionDTO.setCliente(null);  // Evita la referencia circular
        return valoracionDTO;
    }

    private ValoracionDeEstablecimientoDTO mapearValoracionDeEstablecimientoDTOExcluyendoReferencias(ValoracionDeEstablecimiento valoracionDeEstablecimiento) {
        ValoracionDeEstablecimientoDTO valoracionDTO = modelMapper.map(valoracionDeEstablecimiento, ValoracionDeEstablecimientoDTO.class);
        // Excluir referencias tanto al establecimiento como al cliente
        valoracionDTO.setEstablecimiento(null);  // Evita la referencia circular
        valoracionDTO.setCliente(null);  // Evita la referencia circular
        return valoracionDTO;
    }
}
