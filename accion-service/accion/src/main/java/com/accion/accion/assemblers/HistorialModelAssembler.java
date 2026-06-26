package com.accion.accion.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.accion.accion.controller.v2.HistorialAccionesControllerV2;
import com.accion.accion.DTO.HistorialAccionesDTO; 

@Component
public class HistorialModelAssembler implements RepresentationModelAssembler<HistorialAccionesDTO, EntityModel<HistorialAccionesDTO>> {

    @Override
    public EntityModel<HistorialAccionesDTO> toModel(HistorialAccionesDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(HistorialAccionesControllerV2.class).todas()).withSelfRel()
        );
    }
}

