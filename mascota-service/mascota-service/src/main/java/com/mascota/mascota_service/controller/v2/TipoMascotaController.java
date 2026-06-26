package com.mascota.mascota_service.controller.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mascota.mascota_service.DTO.TipoMascotaDTO;
import com.mascota.mascota_service.assemblers.TipoMascotaAssembler;
import com.mascota.mascota_service.service.TipoMascotaService;

@RestController
@RequestMapping("/api/v2/tipomascota")
public class TipoMascotaController {
    @Autowired
    private TipoMascotaService tipoMascotaService;

    @Autowired
    private TipoMascotaAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<TipoMascotaDTO>>> allTiposMascota() {

        List<EntityModel<TipoMascotaDTO>> tipos = tipoMascotaService.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (tipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                CollectionModel.of(tipos,
                        linkTo(methodOn(TipoMascotaController.class)
                                .allTiposMascota())
                                .withSelfRel()));                           
    }


    @PostMapping(value = "/crear-tipos", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoMascotaDTO>> crearTiposMascotas() {

        try {

            TipoMascotaDTO tipo = tipoMascotaService.creartipo();

            return ResponseEntity
                    .created(linkTo(methodOn(TipoMascotaController.class)
                            .allTiposMascota()).toUri())
                    .body(assembler.toModel(tipo));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
