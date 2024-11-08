package com.gestor.eventos.controller;

import com.gestor.eventos.dto.EventoDTO;
import com.gestor.eventos.dto.EventoRespuesta;
import com.gestor.eventos.services.EventoServicio;
import com.gestor.eventos.utilities.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios/eventos")
public class EventoControlador {

    @Autowired
    private EventoServicio eventoServicio;

    @GetMapping
    public ResponseEntity<EventoRespuesta> listarEventos(
            @RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordernarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

        EventoRespuesta respuesta = eventoServicio.obtenerTodosEventos(numeroDePagina, medidaDePagina, ordernarPor, sortDir);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<EventoDTO> obtenerEventoPorId(@PathVariable(value = "eventoId") Long eventoId) {
        EventoDTO evento = eventoServicio.obtenerEventoPorId(eventoId);
        return ResponseEntity.ok(evento);
    }

    @GetMapping("/grupo/{grupoId}")
    public List<EventoDTO> listarEventosPorGrupoId(@PathVariable(value = "grupoId") Long grupoId) {
        return eventoServicio.obtenerEventosPorGrupoId(grupoId);
    }

    @GetMapping("/establecimiento/{establecimientoId}")
    public List<EventoDTO> listarEventosPorEstablecimientoId(@PathVariable(value = "establecimientoId") Long establecimientoId) {
        return eventoServicio.obtenerEventosPorEstablecimientoId(establecimientoId);
    }

    @PreAuthorize("hasRole('ESTABLECIMIENTO)")
    @PostMapping("/{establecimientoId}/{grupoId}")
    public ResponseEntity<EventoDTO> guardarEvento(@PathVariable(value = "establecimientoId") long establecimientoId, @PathVariable(value = "grupoId") long grupoId , @RequestBody EventoDTO eventoDTO) {
        return new ResponseEntity<>(eventoServicio.crearEvento(establecimientoId, grupoId,eventoDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ESTABLECIMIENTO)")
    @PutMapping("/evento/{eventoId}")
    public ResponseEntity<EventoDTO> actualizarEvento(@PathVariable(value = "eventoId") Long eventoId,@RequestBody EventoDTO eventoDTO) {
        EventoDTO eventoActualizado = eventoServicio.actualizarEvento(eventoId, eventoDTO);
        return new ResponseEntity<>(eventoActualizado, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ESTABLECIMIENTO)")
    @DeleteMapping("/evento/{eventoId}")
    public ResponseEntity<String> eliminarEvento(@PathVariable(value = "eventoId") Long eventoId){
        eventoServicio.eliminarEvento(eventoId);
        return new ResponseEntity<>("Evento eliminado con exito", HttpStatus.OK);
    }

    @GetMapping("/verificar/{establecimientoId}/{grupoId}/{fechaEvento}")
    public ResponseEntity<Boolean> verificarDisponibilidad(@PathVariable(value = "establecimientoId") Long establecimientoId, @PathVariable(value = "grupoId") Long grupoId, @PathVariable(value = "fechaEvento") LocalDate fechaEvento) {
        boolean disponible = eventoServicio.verificarDisponibilidad(establecimientoId, grupoId, fechaEvento);
        return ResponseEntity.ok(disponible);
    }

}
