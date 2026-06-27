package com.example.Tienda_Gestion.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.Tienda_Gestion.Controller.v2.TiendaController;
import com.example.Tienda_Gestion.DTO.TiendaDTO;

@Component
public class TiendaModelAssembler implements RepresentationModelAssembler<TiendaDTO, EntityModel<TiendaDTO>> {

    @Override
    public EntityModel<TiendaDTO> toModel(TiendaDTO tienda) {
        return EntityModel.of(tienda,
                linkTo(methodOn(TiendaController.class).porId(tienda.getIdTienda())).withSelfRel(),
                linkTo(methodOn(TiendaController.class).todas()).withRel("tiendas")
        );
    }
}
