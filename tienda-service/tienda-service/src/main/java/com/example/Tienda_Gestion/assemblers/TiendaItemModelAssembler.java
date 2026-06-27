package com.example.Tienda_Gestion.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.Tienda_Gestion.Controller.v2.TiendaItemController;
import com.example.Tienda_Gestion.DTO.TiendaItemDTO;

@Component
public class TiendaItemModelAssembler implements RepresentationModelAssembler<TiendaItemDTO, EntityModel<TiendaItemDTO>> {

    @Override
    public EntityModel<TiendaItemDTO> toModel(TiendaItemDTO tiendaItem) {
        return EntityModel.of(
                tiendaItem,
                linkTo(methodOn(TiendaItemController.class).agregarItemaTienda(null)).withSelfRel(),
                linkTo(methodOn(TiendaItemController.class).comprarItem(1, tiendaItem.getIdTiendaItem()))
                        .withRel("comprar-item"));
    }
}
