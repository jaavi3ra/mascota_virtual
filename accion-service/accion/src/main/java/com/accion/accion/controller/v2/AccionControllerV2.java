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
import org.springframework.web.bind.annotation.RequestParam;
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
            AccionDTO dto = accionService.buscarPorIdDTO(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<AccionDTO>> registrar(@Valid @RequestBody Accion accion) {
        try {
            AccionDTO dto = accionService.guardarDTO(accion);
            return ResponseEntity
                    .created(linkTo(methodOn(AccionControllerV2.class).porId(dto.getIdAccion())).toUri())
                    .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/usar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<String>> usarItem(
            @RequestParam Integer idMascota,
            @RequestParam Integer idItem) {
        try {
            String mensaje = accionService.ejecutarAccionDeItem(idMascota, idItem);
            EntityModel<String> respuesta = EntityModel.of(
                    mensaje,
                    linkTo(methodOn(AccionControllerV2.class).usarItem(idMascota, idItem)).withSelfRel(),
                    linkTo(methodOn(AccionControllerV2.class).todas()).withRel("acciones"));
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}


