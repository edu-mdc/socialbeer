package com.gestor.eventos.controller;

import com.gestor.eventos.dto.ClienteDTO;
import com.gestor.eventos.dto.ClienteRespuesta;
import com.gestor.eventos.exceptions.ResourceNotFoundException;
import com.gestor.eventos.services.ClienteServicio;
import com.gestor.eventos.utilities.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios/clientes")
public class ClienteControllador {

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping
    public ResponseEntity<ClienteRespuesta> listarClientes(
            @RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordernarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

        ClienteRespuesta respuesta = clienteServicio.obtenerTodosLosClientes(numeroDePagina, medidaDePagina, ordernarPor, sortDir);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ClienteDTO> obtenerClientePorUsuarioId(@PathVariable(value = "usuarioId") Long usuarioId) {
         try {
            // Intentar obtener el cliente por usuarioId
            ClienteDTO clienteDTO = clienteServicio.obtenerClientePorId(usuarioId);
            return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            // Si no se encuentra, devolver un 404 o crear el cliente
            // Aquí puedes decidir si devuelves una respuesta vacía o creas un nuevo cliente
            //ClienteDTO nuevoCliente = clienteServicio.crearClienteNuevoPorUsuarioId(usuarioId);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }
    }

    @PostMapping(value = "/{usuarioId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> guardarCliente(@PathVariable(value = "usuarioId") long usuarioId, @RequestBody ClienteDTO clienteDTO) {
        return new ResponseEntity<>(clienteServicio.crearCliente(usuarioId, clienteDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable(value = "clienteId") Long clienteId,@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteActualizado = clienteServicio.actualizarCliente(clienteId, clienteDTO);
        return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<String> eliminarComentario(@PathVariable(value = "clienteId") Long clienteId){
        clienteServicio.eliminarCliente(clienteId);
        return new ResponseEntity<>("cliente eliminado con exito", HttpStatus.OK);
    }
}
