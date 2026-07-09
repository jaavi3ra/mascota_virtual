package com.example.Inventario_Gestion.Assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.Inventario_Gestion.Controller.V2.InventarioController;
import com.example.Inventario_Gestion.DTO.InventarioDTO;

@Component
public class InventarioModelAssembler
                implements RepresentationModelAssembler<InventarioDTO, EntityModel<InventarioDTO>> {

        @Override
        public EntityModel<InventarioDTO> toModel(InventarioDTO inventario) {
                return EntityModel.of(inventario,
                                linkTo(methodOn(InventarioController.class)
                                                .buscarporidInventario(inventario.getId_inven()))
                                                .withSelfRel(),
                                linkTo(methodOn(InventarioController.class)
                                                .listarItemdDeInventario(inventario.getUsuario()))
                                                .withRel("inventario-usuario"),
                                linkTo(methodOn(InventarioController.class)
                                                .obtenerItemenInventario(inventario.getUsuario(), inventario.getItem()))
                                                .withRel("item-en-inventario"));
        }
}
