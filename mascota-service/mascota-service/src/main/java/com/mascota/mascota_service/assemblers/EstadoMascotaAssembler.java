package com.mascota.mascota_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Component;

import com.mascota.mascota_service.DTO.EstadoMascotaDTO;
import com.mascota.mascota_service.controller.v2.EstadoMascotaController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
// permite la inyeccion de otras dependencias, no solo get y set, si no tambien objetos.
@Component
// assembler se encarga de pasar un modeloBD a un dto sin exponer datos
//RepresentationModel es un contenedor que envuelce los datos y les añade enlaces/links
public class EstadoMascotaAssembler implements RepresentationModelAssembler<EstadoMascotaDTO, EntityModel<EstadoMascotaDTO>>{
       
        @SuppressWarnings("null")
        @Override
        //entityModel es un modelo con enlaces hipermedia
        public EntityModel<EstadoMascotaDTO> toModel(EstadoMascotaDTO estadoMascota) {

            return EntityModel.of(
                    estadoMascota,
                    linkTo(methodOn(EstadoMascotaController.class).editarEstado(estadoMascota.getIdEstadoMascota(), null))
                            .withSelfRel()
                    );
                    // linkTo = genera automaticamente el url dinamico que apunta al metodo del controlador
                    // withselRel = url generada es el enlace propio de este recurso
    }                
}
