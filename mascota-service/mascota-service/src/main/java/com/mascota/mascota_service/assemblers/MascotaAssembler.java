package com.mascota.mascota_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.mascota.mascota_service.DTO.MascotaDTO;
import com.mascota.mascota_service.controller.v2.MascotaController;
import com.mascota.mascota_service.model.Mascota;

@Component
public class MascotaAssembler implements RepresentationModelAssembler<MascotaDTO, EntityModel<MascotaDTO>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<MascotaDTO> toModel(MascotaDTO mascota) {

        return EntityModel.of(
                mascota,
                linkTo(methodOn(MascotaController.class)
                        .buscarMascota(mascota.getIdMascota()))
                        .withSelfRel(),

                linkTo(methodOn(MascotaController.class)
                        .crearMascota(
                                mascota.getIdUsuarioFk(),
                                mascota.getNombre(),
                                mascota.getTipoMascota()))
                        .withRel("crear-mascota"),

                linkTo(methodOn(MascotaController.class)
                        .actualizarExp(0, new Mascota()))
                        .withRel("actualizar-exp"),

                linkTo(methodOn(MascotaController.class)
                        .guardarMascota(new Mascota()))
                        .withRel("guardar")
        );
    }
}
