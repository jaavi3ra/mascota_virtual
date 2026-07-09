package com.mascota.mascota_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.mascota.mascota_service.DTO.TipoMascotaDTO;
import com.mascota.mascota_service.controller.v2.TipoMascotaController;

@Component
public class TipoMascotaAssembler implements RepresentationModelAssembler<TipoMascotaDTO, EntityModel<TipoMascotaDTO>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<TipoMascotaDTO> toModel(TipoMascotaDTO tipoMascota) {

        return EntityModel.of( tipoMascota,
                linkTo(methodOn(TipoMascotaController.class)
                        .allTiposMascota())
                        .withRel("tipos-mascota"),

                linkTo(methodOn(TipoMascotaController.class)
                        .crearTiposMascotas())
                        .withRel("crear-tipos")
        );
    }
}
