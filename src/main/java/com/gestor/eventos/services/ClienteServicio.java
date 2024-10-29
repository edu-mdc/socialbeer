package com.gestor.eventos.services;

import com.gestor.eventos.dto.ClienteDTO;
import com.gestor.eventos.dto.ClienteRespuesta;


public interface ClienteServicio {


    public ClienteDTO crearCliente(long usuarioId, ClienteDTO clienteDTO);

    public ClienteDTO crearClienteNuevoPorUsuarioId(Long usuarioId);

    public ClienteDTO obtenerClientePorId (Long clienteId);

    ClienteRespuesta obtenerTodosLosClientes(int numeroDePagina, int medidaDePagina, String ordernarPor, String sortDir);

    public ClienteDTO actualizarCliente(Long clienteId, ClienteDTO solicitudDeCliente);

    public void eliminarCliente(Long clienteId);

    public boolean existeDniEnOtroCliente(String dni, Long clienteIdActual);
}
