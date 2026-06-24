package com.accion.accion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accion.accion.DTO.AccionDTO;
import com.accion.accion.model.Accion;
import com.accion.accion.service.AccionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/accion")
public class AccionController {

    @Autowired
    private AccionService accionService;

    @GetMapping
    public ResponseEntity<List<AccionDTO>> listar() {
        return new ResponseEntity<>(accionService.obtenerTodas(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Accion accion) {
        try {
            return new ResponseEntity<>(accionService.guardar(accion), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(accionService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}

