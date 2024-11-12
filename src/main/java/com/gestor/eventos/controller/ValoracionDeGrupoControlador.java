package com.gestor.eventos.controller;

import com.gestor.eventos.dto.ValoracionDeGrupoDTO;
import com.gestor.eventos.services.ValoracionDeGrupoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios/valoracionGrupos")
public class ValoracionDeGrupoControlador {

    @Autowired
    private ValoracionDeGrupoServicio valoracionDeGrupoServicio;

    @GetMapping("/{grupoId}")
    public List<ValoracionDeGrupoDTO> listarValoracionesDeGrupoPorGrupoId(@PathVariable(value = "grupoId") Long grupoId) {
        return valoracionDeGrupoServicio.obtenerValoracionDeGrupoPorGrupoId(grupoId);
    }

    @GetMapping("{grupoId}/{valoracionDeGrupoid}")
    public ResponseEntity<ValoracionDeGrupoDTO> obtenerValoracionDeGrupoPorId(@PathVariable(value = "grupoId") Long grupoId, @PathVariable(value = "valoracionDeGrupoid") Long valoracionDeGrupoid) {
        ValoracionDeGrupoDTO valoracionDeGrupoDTO = valoracionDeGrupoServicio.obtenerValoracionDeGrupoPorID(grupoId, valoracionDeGrupoid);
        return new ResponseEntity<>(valoracionDeGrupoDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/{grupoId}/{clienteId}")
    public ResponseEntity<ValoracionDeGrupoDTO> guardarValoracionDeGrupo(@PathVariable(value = "grupoId") long grupoId, @PathVariable(value = "clienteId") long clienteId , @RequestBody ValoracionDeGrupoDTO valoracionDeGrupoDTO) {
        return new ResponseEntity<>(valoracionDeGrupoServicio.crearValoracionDeGrupo(grupoId, clienteId,valoracionDeGrupoDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENTE)")
    @PutMapping("{grupoId}/{valoracionDeGrupoid}")
    public ResponseEntity<ValoracionDeGrupoDTO> actualizarValoracionDeGrupo(@PathVariable(value = "grupoId") Long grupoId, @PathVariable(value = "valoracionDeGrupoid") Long valoracionDeGrupoid,@RequestBody ValoracionDeGrupoDTO valoracionDeGrupoDTO) {
        ValoracionDeGrupoDTO valoracionDeGrupoActualizada = valoracionDeGrupoServicio.actualizarValoracionDeGrupo(grupoId, valoracionDeGrupoid, valoracionDeGrupoDTO);
        return new ResponseEntity<>(valoracionDeGrupoActualizada, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENTE)")
    @DeleteMapping("{grupoId}/{valoracionDeGrupoid}")
    public ResponseEntity<String> eliminarValoracionDeGrupo(@PathVariable(value = "grupoId") Long grupoId, @PathVariable(value = "valoracionDeGrupoid") Long valoracionDeGrupoid){
        valoracionDeGrupoServicio.eliminarValoracionDeGrupo(grupoId,valoracionDeGrupoid);
        return new ResponseEntity<>("ValorarionDeGrupo eliminada con exito", HttpStatus.OK);
    }
}
