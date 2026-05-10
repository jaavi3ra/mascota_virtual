package com.example.mascotav.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mascotav.DTO.TipoMascotaDTO;
import com.example.mascotav.service.TipoMascotaService;

@RestController
@RequestMapping("/api/v1/tipo-mascota")

public class TipoMascotaController {

    @Autowired
    private TipoMascotaService tipoMascotaService;

    @GetMapping
    public ResponseEntity<List<TipoMascotaDTO>> listarTipos() {
        List<TipoMascotaDTO> tipos = tipoMascotaService.obtenerTodos();

        if (tipos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }
}