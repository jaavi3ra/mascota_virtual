package com.example.mascotav.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mascotav.DTO.InventarioDTO;
import com.example.mascotav.service.InventarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/{iduser}")
    public ResponseEntity<?> listarItemdDeInventario(@Valid @PathVariable Integer iduser) {
        try{
             List<InventarioDTO> items = inventarioService.listarItemdelInventario(iduser);
        return items.isEmpty() 
            ? new ResponseEntity<>("No hay items en el inventario de este usuario.",HttpStatus.OK) 
            : new ResponseEntity<>(items, HttpStatus.OK);
        }catch(RuntimeException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
       
    }

    @PutMapping("/{idmascota}/darItem/{iditem}")
    // editar estado de mascotaid con itemId
    public ResponseEntity<?> usarItem(@Valid @PathVariable Integer idmascota,@Valid @PathVariable Integer iditem){
        try{
           String mensaje = inventarioService.usarItem(idmascota, iditem);
            return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
            
        }catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
