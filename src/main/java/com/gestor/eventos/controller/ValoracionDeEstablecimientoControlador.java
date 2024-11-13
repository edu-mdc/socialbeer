package com.gestor.eventos.controller;


import com.gestor.eventos.dto.ValoracionDeEstablecimientoDTO;
import com.gestor.eventos.services.ValoracionDeEstablecimientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios/valoracionEstablecimientos")
public class ValoracionDeEstablecimientoControlador {

    @Autowired
    private ValoracionDeEstablecimientoServicio valoracionDeEstablecimientoServicio;

    @GetMapping("/{establecimientoId}")
    public List<ValoracionDeEstablecimientoDTO> listarValoracionesDeEstablecimientoPorEstablecimientoId(@PathVariable(value = "establecimientoId") Long establecimientoId) {
        return valoracionDeEstablecimientoServicio.obtenerValoracionDeEstablecimientoPorestablecimientoId(establecimientoId);
    }

    @GetMapping("{establecimientoId}/{valoracionDeEstablecimientoId}")
    public ResponseEntity<ValoracionDeEstablecimientoDTO> obtenerValoracionDeEstablecimientoPorId(@PathVariable(value = "establecimientoId") Long establecimientoId, @PathVariable(value = "valoracionDeEstablecimientoId") Long valoracionDeEstablecimientoId) {
        ValoracionDeEstablecimientoDTO valoracionDeEstablecimientoDTO = valoracionDeEstablecimientoServicio.obtenerValoracionDeEstablecimientoPorID(establecimientoId, valoracionDeEstablecimientoId);
        return new ResponseEntity<>(valoracionDeEstablecimientoDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENTE)")
    @PostMapping("/{establecimientoId}/{clienteId}")
    public ResponseEntity<ValoracionDeEstablecimientoDTO> guardarValoracionDeEstablecimiento(@PathVariable(value = "establecimientoId") long establecimientoId, @PathVariable(value = "clienteId") long clienteId , @RequestBody ValoracionDeEstablecimientoDTO valoracionDeEstablecimientoDTO) {
        return new ResponseEntity<>(valoracionDeEstablecimientoServicio.crearValoracionDeEstablecimiento(establecimientoId, clienteId,valoracionDeEstablecimientoDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("{establecimientoId}/{valoracionDeEstablecimientoId}")
    public ResponseEntity<ValoracionDeEstablecimientoDTO> actualizarValoracionDeGrupo(@PathVariable(value = "establecimientoId") Long establecimientoId, @PathVariable(value = "valoracionDeEstablecimientoId") Long valoracionDeEstablecimientoId,@RequestBody ValoracionDeEstablecimientoDTO valoracionDeEstablecimientoDTO) {
        ValoracionDeEstablecimientoDTO valoracionDeEstablecimientoActualizada = valoracionDeEstablecimientoServicio.actualizarValoracionDeEstablecimiento(establecimientoId, valoracionDeEstablecimientoId, valoracionDeEstablecimientoDTO);
        return new ResponseEntity<>(valoracionDeEstablecimientoActualizada, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENTE)")
    @DeleteMapping("{establecimientoId}/{valoracionDeEstablecimientoId}")
    public ResponseEntity<String> eliminarValoracionDeEstablecimiento(@PathVariable(value = "establecimientoId") Long establecimientoId, @PathVariable(value = "valoracionDeEstablecimientoId") Long valoracionDeEstablecimientoId){
        valoracionDeEstablecimientoServicio.eliminarValoracionDeEstablecimiento(establecimientoId,valoracionDeEstablecimientoId);
        return new ResponseEntity<>("ValorarionDeEstablecimiento eliminada con exito", HttpStatus.OK);
    }
}
