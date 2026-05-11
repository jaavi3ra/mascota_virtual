package com.example.mascotav.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mascotav.DTO.HistorialAccionesDTO;
import com.example.mascotav.service.HistorialAccionesService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1/historial")
public class HistorialAccionesController {

    @Autowired
    private HistorialAccionesService historialAccionesService;

    @GetMapping
    public ResponseEntity<List<HistorialAccionesDTO>> listar() {
        return new ResponseEntity<>(historialAccionesService.obtenerTodos(), HttpStatus.OK);
    }

    
    
    
}
