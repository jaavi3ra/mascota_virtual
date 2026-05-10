package com.example.mascotav.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mascotav.DTO.InventarioDTO;
import com.example.mascotav.service.InventarioService;

@RestController
@RequestMapping("/api/v1/invetario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<?> listarItemdDeInventario(@PathVariable Integer iduser) {

        List<InventarioDTO> items = inventarioService.listarItemdelInventario(iduser);
        return items.isEmpty() 
            ? new ResponseEntity<>("No hay items en el inventario de este usuario.",HttpStatus.NO_CONTENT) 
            : new ResponseEntity<>(items, HttpStatus.OK);
    }
}
