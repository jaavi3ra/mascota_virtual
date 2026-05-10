package com.example.mascotav.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mascotav.DTO.EvolucionDTO;
import com.example.mascotav.service.EvolucionService;

@RestController
@RequestMapping("/api/v1/evolucion")
public class EvolucionController {

    @Autowired
    private EvolucionService evolucionService;

    @GetMapping
    public ResponseEntity<?> listarEvoluciones() {

        List<EvolucionDTO> evoluciones = evolucionService.obtenerTodas();
        return evoluciones.isEmpty() 
            ? new ResponseEntity<>("No hay evoluciones registradas.",HttpStatus.NO_CONTENT) 
            : new ResponseEntity<>(evoluciones, HttpStatus.OK);
    }

}
