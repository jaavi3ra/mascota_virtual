package com.accion.accion.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accion.accion.DTO.AccionDTO;
import com.accion.accion.assemblers.AccionModelAssembler;
import com.accion.accion.model.Accion;
import com.accion.accion.service.AccionService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("accionControllerV2")
@RequestMapping("/api/v2/accion")
public class AccionControllerV2 {

    @Autowired
    private AccionService accionService;

    @Autowired
    private AccionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<AccionDTO>>> todas() {
        List<EntityModel<AccionDTO>> acciones = accionService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (acciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                acciones,
                linkTo(methodOn(AccionControllerV2.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<AccionDTO>> porId(@PathVariable Integer id) {
        try {
            Accion accion = accionService.buscarPorId(id);
            if (accion == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Convertimos la entidad de la v1 al DTO que pide el assembler
            AccionDTO dto = new AccionDTO();
            dto.setIdAccion(accion.getIdAccion());
            dto.setNombreAccion(accion.getNombreAccion());
            dto.setAfectaFelicidad(accion.getAfectaFelicidad());
            dto.setAfectaEnergia(accion.getAfectaEnergia());
            dto.setAfectaSalud(accion.getAfectaSalud());
            dto.setAfectaHambre(accion.getAfectaHambre());
            dto.setAfectaExpBase(accion.getAfectaExpBase());
            
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<AccionDTO>> registrar(@Valid @RequestBody Accion accion) {
        try {
            Accion nuevaAccion = accionService.guardar(accion);
            
            AccionDTO dto = new AccionDTO();
            dto.setIdAccion(nuevaAccion.getIdAccion());
            dto.setNombreAccion(nuevaAccion.getNombreAccion());
            
            return ResponseEntity
                    .created(linkTo(methodOn(AccionControllerV2.class).porId(dto.getIdAccion())).toUri())
                    .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}


