package com.example.mascotav.controller;

import java.util.List;
import com.example.mascotav.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.mascotav.DTO.AccionDTO;
import com.example.mascotav.model.Accion;
import com.example.mascotav.model.Mascota;
import com.example.mascotav.repository.ItemRepository;
import com.example.mascotav.repository.MascotaRepository;
import com.example.mascotav.service.AccionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/accion")
public class AccionController {
    @Autowired
    private AccionService accionService;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<List<AccionDTO>> listar() {
        return new ResponseEntity<>(accionService.obtenerTodas(), HttpStatus.OK);
    }

    @PostMapping
 
        public ResponseEntity<?> crear(@RequestBody Accion accion) {
            try{
                 return new ResponseEntity<>(accionService.guardar(accion),HttpStatus.CREATED);
            }catch(RuntimeException e){
                 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
       
        }

   
}

