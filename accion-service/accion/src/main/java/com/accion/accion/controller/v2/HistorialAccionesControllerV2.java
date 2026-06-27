package com.accion.accion.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accion.accion.DTO.HistorialAccionesDTO;
import com.accion.accion.assemblers.HistorialModelAssembler;
import com.accion.accion.service.HistorialAccionesService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("historialAccionesControllerV2")
@RequestMapping("/api/v2/historial")
public class HistorialAccionesControllerV2 {

    @Autowired
    private HistorialAccionesService historialAccionesService;

    @Autowired
    private HistorialModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<HistorialAccionesDTO>>> todas() {
        List<EntityModel<HistorialAccionesDTO>> registros = historialAccionesService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (registros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                registros,
                linkTo(methodOn(HistorialAccionesControllerV2.class).todas()).withSelfRel()
        ));
    }
}


