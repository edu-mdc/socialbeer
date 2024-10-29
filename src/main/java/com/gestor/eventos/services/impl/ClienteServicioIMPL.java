package com.gestor.eventos.services.impl;

import com.gestor.eventos.dto.*;
import com.gestor.eventos.entities.*;
import com.gestor.eventos.exceptions.ResourceConflictException;
import com.gestor.eventos.exceptions.ResourceNotFoundException;
import com.gestor.eventos.repository.ClienteRepositorioI;
import com.gestor.eventos.repository.UsuarioRepositorioI;
import com.gestor.eventos.services.ClienteServicio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClienteServicioIMPL implements ClienteServicio {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClienteRepositorioI clienteRepositorioI;

    @Autowired
    private UsuarioRepositorioI usuarioRepositorioI;

    @Override
    @Transactional
    public ClienteDTO crearCliente(long usuarioId, ClienteDTO clienteDTO) {
        // Verificar si el DNI ya está registrado
        if (clienteRepositorioI.existsByDni(clienteDTO.getDni())) {
            throw new ResourceConflictException("El DNI ya está registrado en el sistema.");
        }

        // Continuar con la creación si el DNI no está registrado
        Usuario usuario = usuarioRepositorioI.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));
        Cliente cliente = mapearEntidad(clienteDTO);
        cliente.setUsuario(usuario);
        Cliente nuevoCliente = clienteRepositorioI.save(cliente);

        return mapearDTO(nuevoCliente);
    }

    @Override
    public ClienteDTO obtenerClientePorId(Long usuarioId) {
        Cliente cliente = clienteRepositorioI.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "usuarioId", usuarioId));

        return mapearDTO(cliente);
    }


    @Override
    @Transactional
    public ClienteDTO actualizarCliente(Long usuarioId, ClienteDTO clienteDTO) {
        // Buscar el cliente actual
        Cliente cliente = clienteRepositorioI.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "usuarioId", usuarioId));

        // Buscar si existe otro cliente con el mismo DNI
        Optional<Cliente> clienteConMismoDni = clienteRepositorioI.findByDni(clienteDTO.getDni());

        // Si existe otro cliente con el mismo DNI y no es el mismo que el actual, devolver un mensaje
        if (clienteConMismoDni.isPresent() && !clienteConMismoDni.get().getId().equals(cliente.getId())) {
            throw new ResourceConflictException("El DNI ya está registrado en otro cliente.");
        }

        // Actualizar los datos del cliente
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido_1(clienteDTO.getApellido_1());
        cliente.setApellido_2(clienteDTO.getApellido_2());
        cliente.setDni(clienteDTO.getDni());
        cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setPoblacion(clienteDTO.getPoblacion());
        cliente.setProvincia(clienteDTO.getProvincia());

        // Guardar el cliente actualizado
        Cliente clienteActualizado = clienteRepositorioI.save(cliente);
        return mapearDTO(clienteActualizado);
    }

    public ClienteDTO crearClienteNuevoPorUsuarioId(Long usuarioId) {
        // Buscar al usuario por su ID
        Usuario usuario = usuarioRepositorioI.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        // Crear un cliente nuevo y asociarlo al usuario
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setUsuario(usuario);
        nuevoCliente.setNombre("");
        nuevoCliente.setApellido_1("");
        nuevoCliente.setApellido_2("");// Puedes añadir valores por defecto
        nuevoCliente.setDni("");

        nuevoCliente.setDireccion("");
        nuevoCliente.setProvincia("");
        nuevoCliente.setPoblacion("");

        // Guardar el nuevo cliente en la base de datos
        Cliente clienteGuardado = clienteRepositorioI.save(nuevoCliente);

        // Retornar el cliente guardado como DTO
        return mapearDTO(clienteGuardado);
    }

    @Override
    @Transactional
    public ClienteRespuesta obtenerTodosLosClientes(int numeroDePagina, int medidaDePagina, String ordernarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordernarPor).ascending() : Sort.by(ordernarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        Page<Cliente> clientes = clienteRepositorioI.findAll(pageable);

        List<Cliente> listaDeClientes = clientes.getContent();
        List<ClienteDTO> contenido = listaDeClientes.stream().map(this::mapearDTO).collect(Collectors.toList());

        ClienteRespuesta clienteRespuesta = new ClienteRespuesta();
        clienteRespuesta.setContenido(contenido);
        clienteRespuesta.setNumeroPagina(clientes.getNumber());
        clienteRespuesta.setMedidaPagina(clientes.getSize());
        clienteRespuesta.setTotalElementos(clientes.getTotalElements());
        clienteRespuesta.setTotalPaginas(clientes.getTotalPages());
        clienteRespuesta.setUltima(clientes.isLast());

        return clienteRespuesta;
    }



    @Override
    public void eliminarCliente(Long clienteId) {
        Cliente cliente = clienteRepositorioI.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", clienteId));
        clienteRepositorioI.delete(cliente);
    }

    @Override
    public boolean existeDniEnOtroCliente(String dni, Long clienteIdActual) {
        Optional<Cliente> clienteExistente = clienteRepositorioI.findByDni(dni);
        if (clienteExistente.isPresent()) {
            // Si el cliente con ese DNI existe y no es el que estamos actualizando
            return !clienteExistente.get().getId().equals(clienteIdActual);
        }
        return false;
    }


    private ClienteDTO mapearDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido_1(cliente.getApellido_1());
        clienteDTO.setApellido_2(cliente.getApellido_2());
        clienteDTO.setDni(cliente.getDni());
        clienteDTO.setFechaNacimiento(cliente.getFechaNacimiento());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setDireccion(cliente.getDireccion());
        clienteDTO.setProvincia(cliente.getProvincia());
        clienteDTO.setPoblacion(cliente.getPoblacion());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(cliente.getUsuario().getId());
        usuarioDTO.setNombreUsuario(cliente.getUsuario().getNombreUsuario());
        usuarioDTO.setPassword(cliente.getUsuario().getPassword());
        usuarioDTO.setEmail(cliente.getUsuario().getEmail());
        usuarioDTO.setRol(cliente.getUsuario().getRol());
        clienteDTO.setUsuario(usuarioDTO);

        Set<ValoracionDeGrupoDTO> valoraciones = cliente.getValoracionDeGrupos().stream()
                .map(this::convertirAValoracionGrupoDTO)
                .collect(Collectors.toSet());
        clienteDTO.setValoracionDeGrupos(valoraciones);

        Set<EntradaDTO> entradas = cliente.getEntradas().stream()
                .map(this::convertirAEntradaDTO)
                .collect(Collectors.toSet());
        clienteDTO.setEntradas(entradas);

        return clienteDTO;


    }
    private ValoracionDeGrupoDTO convertirAValoracionGrupoDTO(ValoracionDeGrupo valoracionGrupo) {
        ValoracionDeGrupoDTO dto = new ValoracionDeGrupoDTO();
        dto.setId(valoracionGrupo.getId());
        dto.setPuntuacion(valoracionGrupo.getPuntuacion());
        dto.setComentario(valoracionGrupo.getComentario());
        dto.setFechaValoracion(valoracionGrupo.getFechaValoracion());
        return dto;
    }

    private EntradaDTO convertirAEntradaDTO(Entrada entrada) {
        EntradaDTO dto = new EntradaDTO();
        dto.setId(entrada.getId());
        dto.setFechaCompra(entrada.getFechaCompra());

        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setId(entrada.getEvento().getId());
        eventoDTO.setFechaContratacion(entrada.getEvento().getFechaContratacion());
        eventoDTO.setFechaEvento(entrada.getEvento().getFechaEvento());
        eventoDTO.setHoraEvento(entrada.getEvento().getHoraEvento());
        eventoDTO.setPrecio(entrada.getEvento().getPrecio());
        eventoDTO.setEstado(entrada.getEvento().getEstado());
        dto.setEvento(eventoDTO);

        return dto;
    }

    private Cliente mapearEntidad(ClienteDTO clienteDTO) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);

        return cliente;
    }

    private ValoracionDeGrupoDTO mapearValoracionDeGrupoDTOExcluyendoReferencias(ValoracionDeGrupo valoracionDeGrupo) {
        ValoracionDeGrupoDTO valoracionDTO = modelMapper.map(valoracionDeGrupo, ValoracionDeGrupoDTO.class);
        valoracionDTO.setGrupo(null); // Evita la referencia circular
        valoracionDTO.setCliente(null); // Evita la referencia circular
        return valoracionDTO;
    }

    private ValoracionDeEstablecimientoDTO mapearValoracionDeEstablecimientoDTOExcluyendoReferencias(ValoracionDeEstablecimiento valoracionDeEstablecimiento) {
        ValoracionDeEstablecimientoDTO valoracionDTO = modelMapper.map(valoracionDeEstablecimiento, ValoracionDeEstablecimientoDTO.class);
        valoracionDTO.setEstablecimiento(null); // Evita la referencia circular
        valoracionDTO.setCliente(null); // Evita la referencia circular
        return valoracionDTO;
    }

}
