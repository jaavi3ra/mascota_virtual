package com.mascotav.usuario_service.controller.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mascotav.usuario_service.assemblers.UsuarioAssembler;
import com.mascotav.usuario_service.dto.UsuarioDTO;
import com.mascotav.usuario_service.model.Usuario;
import com.mascotav.usuario_service.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioAssembler assembler;

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> buscarPorId(@PathVariable Integer id) {

        try {

            UsuarioDTO usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(usuario));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> registrarUsuario(
            @Valid @RequestBody Usuario usuario) {

        try {

            UsuarioDTO nuevo = usuarioService.registrarUsuario(usuario);

            return ResponseEntity
                    .created(linkTo(methodOn(UsuarioController.class)
                            .buscarPorId(nuevo.getIdUsuario()))
                            .toUri())
                    .body(assembler.toModel(nuevo));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}