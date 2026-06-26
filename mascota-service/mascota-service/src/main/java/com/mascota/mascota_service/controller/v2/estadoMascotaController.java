package com.mascota.mascota_service.controller.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mascota.mascota_service.DTO.EstadoMascotaDTO;
import com.mascota.mascota_service.assemblers.EstadoMascotaAssembler;
import com.mascota.mascota_service.service.EstadoMascotaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/estado")
public class EstadoMascotaController {

    @Autowired
    private EstadoMascotaService estadoMascotaService;

    @Autowired
    private EstadoMascotaAssembler assembler;

     @PutMapping(value = "/editar-estado/{idestadopet}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EstadoMascotaDTO>> editarEstado(@PathVariable Integer idestadopet, @Valid @RequestBody Integer idaccion) {

        try {
            EstadoMascotaDTO estadoDTO = estadoMascotaService.editarEstado(idestadopet, idaccion);
            return ResponseEntity.ok(assembler.toModel(estadoDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
