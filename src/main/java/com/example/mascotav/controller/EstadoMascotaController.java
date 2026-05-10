package com.example.mascotav.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mascotav.DTO.EstadoMascotaDTO;
import com.example.mascotav.service.EstadoMascotaService;

@RestController
@RequestMapping("/api/v1/estadoMascota")
public class EstadoMascotaController {

    @Autowired
    private EstadoMascotaService estadoMascotaService;

    @GetMapping
    public ResponseEntity<List<EstadoMascotaDTO>> listarTodos() {
        List<EstadoMascotaDTO> estados = estadoMascotaService.obtenerTodos();
        if (estados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(estados, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoMascotaDTO> verEstado(@PathVariable Integer id) {
        try {
            EstadoMascotaDTO estado = estadoMascotaService.buscarPorId(id);
            return new ResponseEntity<>(estado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
