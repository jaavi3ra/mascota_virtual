package com.example.mascotav.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mascotav.service.NivelService;

@RestController
@RequestMapping("/api/v1/nivel")
public class NivelController {
    @Autowired
    private NivelService nivelService;

    public ResponseEntity<String> crearNiveles(){
        nivelService.crearNiveles();
        return new ResponseEntity<>("Niveles creados correctamente.", HttpStatus.CREATED);
    }
}
