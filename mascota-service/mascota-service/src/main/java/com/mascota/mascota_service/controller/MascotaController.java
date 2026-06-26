package com.mascota.mascota_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mascota.mascota_service.DTO.MascotaDTO;
import com.mascota.mascota_service.model.Mascota;
import com.mascota.mascota_service.service.MascotaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/mascota")
public class MascotaController {
    @Autowired
    private MascotaService mascotaService;

    @GetMapping("/buscar-pet/{id}")
    public ResponseEntity<?> buscarMascota(@PathVariable Integer id){
        try{
            MascotaDTO mascotadto = mascotaService.obtenerMascotaId(id);
            return new ResponseEntity<>(mascotadto,HttpStatus.CREATED);     
        }catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    //crear mascota, se crea junto con estado mascota
    @PostMapping("/crear-mascota/{userid}")
    public ResponseEntity<?> crearMascota( @PathVariable Integer userid,@Valid @RequestBody String nombre, Integer idtipo) {
        try {
            MascotaDTO mascotacreada = mascotaService.crearMascota(userid,nombre, idtipo);
            
            Map<String, Object> response = new HashMap<>();

            response.put("Mensaje: ", "Mascota Creada.");
            response.put("Estado Mascota: ", mascotacreada.getTipoMascota());
            return new ResponseEntity<>(mascotacreada, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/actualizarExp/{afectaExpBase}")
    public ResponseEntity<?> actualizarExp(@PathVariable  int afectaExpBase,@Valid @RequestBody Mascota mascota){
        try{
            mascotaService.actualizarExpMascota(mascota, afectaExpBase);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> guardarMascota(@Valid @RequestBody Mascota mascota){
        try{
            MascotaDTO mascotadto = mascotaService.guardarMascota(mascota);
            return new ResponseEntity<>(mascotadto,HttpStatus.CREATED);

        }catch(RuntimeException e ){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
