package com.mascotav.usuario_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.mascotav.usuario_service.controller.v1.UsuarioController;
import com.mascotav.usuario_service.dto.UsuarioDTO;
import com.mascotav.usuario_service.model.Usuario;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<UsuarioDTO, EntityModel<UsuarioDTO>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<UsuarioDTO> toModel(UsuarioDTO usuario) {

        return EntityModel.of(
                usuario,
                linkTo(methodOn(UsuarioController.class)
                        .buscarPorId(usuario.getIdUsuario()))
                        .withSelfRel(),

                linkTo(methodOn(UsuarioController.class)
                        .registrarUsuario(new Usuario()))
                        .withRel("registrar")
        );
    }
}