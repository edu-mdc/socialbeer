package com.gestor.eventos.controller;

import com.gestor.eventos.dto.UsuarioDTO;
import com.gestor.eventos.dto.UsuarioRespuesta;
import com.gestor.eventos.services.UsuarioServicio;
import com.gestor.eventos.utilities.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping
    public UsuarioRespuesta listarUsuarios(
            @RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordernarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

        return usuarioServicio.obtenerTodasLosUsuarios(numeroDePagina, medidaDePagina, ordernarPor, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(usuarioServicio.obtenerUsuariosPorId(id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UsuarioDTO> guardarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(usuarioServicio.crearUsuario(usuarioDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@RequestBody UsuarioDTO usuarioDTO, @PathVariable(name = "id") long id) {
        UsuarioDTO usuarioRespuesta = usuarioServicio.actualizarUsuario(usuarioDTO, id);
        return new ResponseEntity<>(usuarioRespuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable(name = "id") long id) {
        usuarioServicio.eliminarUsuario(id);
        return new ResponseEntity<>("Usuario eliminado con exito", HttpStatus.OK);
    }
}
