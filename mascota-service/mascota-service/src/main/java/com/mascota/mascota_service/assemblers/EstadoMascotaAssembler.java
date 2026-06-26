package com.mascota.mascota_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Component;

import com.mascota.mascota_service.DTO.EstadoMascotaDTO;
import com.mascota.mascota_service.controller.v1.EstadoMascotacontroller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

@Component
public class EstadoMascotaAssembler implements RepresentationModelAssembler<EstadoMascotaDTO, EntityModel<EstadoMascotaDTO>>{
       
        @SuppressWarnings("null")
        @Override
        public EntityModel<EstadoMascotaDTO> toModel(EstadoMascotaDTO estadoMascota) {

            return EntityModel.of(
                    estadoMascota,
                    linkTo(methodOn(EstadoMascotacontroller.class).editarEstado(estadoMascota.getIdEstadoMascota(), null))
                            .withSelfRel()
                    );
    }
}
