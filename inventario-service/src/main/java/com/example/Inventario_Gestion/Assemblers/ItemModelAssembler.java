package com.example.Inventario_Gestion.Assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.Inventario_Gestion.Controller.V2.ItemController;
import com.example.Inventario_Gestion.DTO.ItemDTO;

@Component
public class ItemModelAssembler implements RepresentationModelAssembler<ItemDTO, EntityModel<ItemDTO>> {

    @Override
    public EntityModel<ItemDTO> toModel(ItemDTO item) {
        return EntityModel.of(item,
                linkTo(methodOn(ItemController.class).obtenerItenPorId(item.getIdItem())).withSelfRel(),
                linkTo(methodOn(ItemController.class).listar()).withRel("items"));
    }
}
