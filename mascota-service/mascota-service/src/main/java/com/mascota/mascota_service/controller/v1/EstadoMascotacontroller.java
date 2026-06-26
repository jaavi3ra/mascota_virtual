package com.mascota.mascota_service.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mascota.mascota_service.DTO.EstadoMascotaDTO;
import com.mascota.mascota_service.DTO.MascotaDTO;
import com.mascota.mascota_service.model.EstadoMascota;
import com.mascota.mascota_service.service.EstadoMascotaService;

import jakarta.validation.Valid;
import jakarta.ws.rs.Path;

@RestController
@RequestMapping("/api/v1/estado")
public class EstadoMascotacontroller {
    @Autowired
    private EstadoMascotaService estadoMascotaService;

    @PutMapping("/editar-estado/{idestadopet}")
    public ResponseEntity<?> editarEstado(@PathVariable Integer idestadopet,@Valid @RequestBody  Integer idaccion) {
        try {
            EstadoMascotaDTO estado = estadoMascotaService.editarEstado(idestadopet, idaccion);
            return new ResponseEntity<>(estado, HttpStatus.CREATED);
            
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
