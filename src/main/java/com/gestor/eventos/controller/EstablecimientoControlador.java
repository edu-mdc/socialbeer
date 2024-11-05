package com.gestor.eventos.controller;


import com.gestor.eventos.dto.ClienteDTO;
import com.gestor.eventos.dto.EstablecimientoDTO;
import com.gestor.eventos.dto.EstablecimientoRespuesta;
import com.gestor.eventos.exceptions.ResourceNotFoundException;
import com.gestor.eventos.services.EstablecimientoServicio;
import com.gestor.eventos.utilities.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios/establecimientos")
public class EstablecimientoControlador {

    @Autowired
    private EstablecimientoServicio establecimientoServicio;

    @GetMapping
    public ResponseEntity<EstablecimientoRespuesta> listarEstablecimientos(
            @RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordernarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

        EstablecimientoRespuesta respuesta = establecimientoServicio.obtenerTodosLosEstablecimientos(numeroDePagina, medidaDePagina, ordernarPor, sortDir);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("usuario/{usuarioId}")
    public ResponseEntity<EstablecimientoDTO> obtenerEstablecimientoPorUsuarioId(@PathVariable(value = "usuarioId") Long usuarioId) {
        try {
            EstablecimientoDTO establecimientoDTO = establecimientoServicio.obtenerEstablecimientoPorId(usuarioId);
            return new ResponseEntity<>(establecimientoDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
           // EstablecimientoDTO nuevoEstablecimiento = establecimientoServicio.crearEstablecimientoNuevoPorUsuarioId(usuarioId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    }

    @PostMapping(value = "/{usuarioId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstablecimientoDTO> guardarEstablecimiento(@PathVariable(value = "usuarioId") long usuarioId, @RequestBody EstablecimientoDTO establecimientoDTO) {
        return new ResponseEntity<>(establecimientoServicio.crearEstablecimiento(usuarioId, establecimientoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{establecimientoId}")
    public ResponseEntity<EstablecimientoDTO> actualizarGrupo(@PathVariable(value = "establecimientoId") Long establecimientoId,@RequestBody EstablecimientoDTO establecimientoDTO) {
        EstablecimientoDTO establecimientoActualizado = establecimientoServicio.actualizarEstablecimiento(establecimientoId, establecimientoDTO);
        return new ResponseEntity<>(establecimientoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{establecimientoId}")
    public ResponseEntity<String> eliminarGrupo(@PathVariable(value = "establecimientoId") Long establecimientoId){
        establecimientoServicio.eliminarEstablecimiento(establecimientoId);
        return new ResponseEntity<>("Establecimiento eliminado con exito", HttpStatus.OK);
    }
}
