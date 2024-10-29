package com.gestor.eventos.controller;

import com.gestor.eventos.dto.EntradaDTO;
import com.gestor.eventos.services.EntradaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios/entradas")
public class EntradaControlador {

    @Autowired
    private EntradaServicio entradaServicio;



    @GetMapping("/{eventoId}")
    public List<EntradaDTO> listarEntradaPorEventoId(@PathVariable(value = "eventoId") Long eventoId) {
        return entradaServicio.obtenerEntradasPorEventoId(eventoId);
    }

    @GetMapping("cliente/{clienteId}")
    public List<EntradaDTO> listarEntradaPorClienteId(@PathVariable(value = "clienteId") Long clienteId) {
        return entradaServicio.obtenerEntradasPorClienteId(clienteId);
    }

    @GetMapping("cliente/{clienteId}/{eventoId}")
    public List<EntradaDTO> listarTodasLasEntradasPorClienteIdDeUnEventoId(@PathVariable(value = "clienteId") Long clienteId,@PathVariable(value = "eventoId") Long eventoId) {
        return entradaServicio.obtenerEntradasPorClienteIdDeUnEventoId(clienteId,eventoId );
    }

    @GetMapping("{eventoId}/{entradaId}")
    public ResponseEntity<EntradaDTO> obtenerEntradaPorId(@PathVariable(value = "eventoId") Long eventoId, @PathVariable(value = "entradaId") Long entradaId) {
        EntradaDTO entradaDTO = entradaServicio.obtenerEntradaPorID(eventoId, entradaId);
        return new ResponseEntity<>(entradaDTO, HttpStatus.OK);
    }

    @PostMapping("/{eventoId}/{clienteId}")
    public ResponseEntity<EntradaDTO> guardarEntrada(@PathVariable(value = "eventoId") long eventoId, @PathVariable(value = "clienteId") long clienteId , @RequestBody EntradaDTO entradaDTO) {
        return new ResponseEntity<>(entradaServicio.crearEntrada(eventoId, clienteId,entradaDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("{eventoId}/{entradaId}")
    public ResponseEntity<String> eliminarEntrada(@PathVariable(value = "eventoId") Long eventoId, @PathVariable(value = "entradaId") Long entradaId){
        entradaServicio.eliminarEntrada(eventoId,entradaId);
        return new ResponseEntity<>("Entrada eliminada con exito", HttpStatus.OK);
    }


}
