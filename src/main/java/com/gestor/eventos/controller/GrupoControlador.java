package com.gestor.eventos.controller;

import com.gestor.eventos.dto.GrupoDTO;
import com.gestor.eventos.dto.GrupoRespuesta;
import com.gestor.eventos.services.GrupoServicio;
import com.gestor.eventos.utilities.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios/grupos")
public class GrupoControlador {

    @Autowired
    private GrupoServicio grupoServicio;

    @GetMapping
    public ResponseEntity<GrupoRespuesta> listarGrupos(
            @RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordernarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

        GrupoRespuesta respuesta = grupoServicio.obtenerTodosLosGrupos(numeroDePagina, medidaDePagina, ordernarPor, sortDir);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{grupoId}")
    public ResponseEntity<GrupoDTO> obtenerGrupoPorId(@PathVariable(value = "grupoId") Long grupoId) {
        GrupoDTO grupoDTO = grupoServicio.obtenerGrupoPorId(grupoId);
        return new ResponseEntity<>(grupoDTO, HttpStatus.OK);
    }

    @PostMapping("/{usuarioId}")
    public ResponseEntity<GrupoDTO> guardarGrupo(@PathVariable(value = "usuarioId") long usuarioId, @RequestBody GrupoDTO grupoDTO) {
        return new ResponseEntity<>(grupoServicio.crearGrupo(usuarioId, grupoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<GrupoDTO> actualizarGrupo(@PathVariable(value = "grupoId") Long grupoId,@RequestBody GrupoDTO grupoDTO) {
        GrupoDTO grupoActualizado = grupoServicio.actualizarGrupo(grupoId, grupoDTO);
        return new ResponseEntity<>(grupoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{grupoId}")
    public ResponseEntity<String> eliminarGrupo(@PathVariable(value = "grupoId") Long grupoId){
        grupoServicio.eliminarGrupo(grupoId);
        return new ResponseEntity<>("Grupo eliminado con exito", HttpStatus.OK);
    }
}
