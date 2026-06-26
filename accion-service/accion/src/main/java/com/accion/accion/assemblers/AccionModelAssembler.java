package com.accion.accion.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.accion.accion.controller.v2.AccionControllerV2;
import com.accion.accion.DTO.AccionDTO; 

@Component
public class AccionModelAssembler implements RepresentationModelAssembler<AccionDTO, EntityModel<AccionDTO>> {

    @Override
    public EntityModel<AccionDTO> toModel(AccionDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(AccionControllerV2.class).porId(dto.getIdAccion())).withSelfRel(),
                linkTo(methodOn(AccionControllerV2.class).todas()).withRel("acciones")
        );
    }
}


