package com.example.mascotav.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mascotav.DTO.InventarioDTO;
import com.example.mascotav.service.InventarioService;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/{iduser}")
    public ResponseEntity<?> listarItemdDeInventario(@PathVariable Integer iduser) {
        try{
             List<InventarioDTO> items = inventarioService.listarItemdelInventario(iduser);
        return items.isEmpty() 
            ? new ResponseEntity<>("No hay items en el inventario de este usuario.",HttpStatus.OK) 
            : new ResponseEntity<>(items, HttpStatus.OK);
        }catch(RuntimeException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
       
    }

    @PostMapping("/{idmascota}/darItem/{iditem}")
    public ResponseEntity<?> usarItem(@PathVariable Integer idmascota,@PathVariable Integer iditem){
        try{
            inventarioService.usarItem(idmascota, iditem);
            return new ResponseEntity<>("Item entregado a Mascota", HttpStatus.CREATED);
            
        }catch(RuntimeException e){
            return new ResponseEntity<>("No se pudo dar el item a la mascota :(", HttpStatus.BAD_REQUEST);
        }
    }
}
