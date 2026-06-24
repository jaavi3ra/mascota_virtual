package com.accion.accion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.accion.accion.model.Accion;
import com.accion.accion.service.HistorialAccionesService;

@RestController
@RequestMapping("/api/v1/historial")
public class HistorialAccionesController {

    @Autowired
    private HistorialAccionesService historialAccionesService;

    @PostMapping("/mascota/{idMascota}")
    public ResponseEntity<?> crearHistorial(@PathVariable Integer idMascota, @RequestBody Accion accion, @RequestParam String descripcion) {
        try {
            return new ResponseEntity<>(historialAccionesService.guardar(idMascota, accion, descripcion), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

