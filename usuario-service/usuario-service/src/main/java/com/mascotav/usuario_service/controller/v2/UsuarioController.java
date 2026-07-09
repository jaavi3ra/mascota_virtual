package com.mascotav.usuario_service.controller.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mascotav.usuario_service.assemblers.UsuarioModelAssembler;
import com.mascotav.usuario_service.dto.UsuarioDTO;
import com.mascotav.usuario_service.model.Usuario;
import com.mascotav.usuario_service.service.UsuarioService;

import jakarta.validation.Valid;

@RestController("usuarioControllerV2")
@RequestMapping("/api/v2/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> porId(@PathVariable Integer id) {
        try {
            UsuarioDTO usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> registrar(@Valid @RequestBody Usuario usuario) {
        try {
            UsuarioDTO nuevo = usuarioService.registrarUsuario(usuario);
            return ResponseEntity
                    .created(linkTo(methodOn(UsuarioController.class).porId(nuevo.getIdUsuario())).toUri())
                    .body(assembler.toModel(nuevo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
