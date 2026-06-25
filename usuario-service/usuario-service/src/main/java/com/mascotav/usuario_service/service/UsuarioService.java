package com.mascotav.usuario_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotav.usuario_service.dto.UsuarioDTO;
import com.mascotav.usuario_service.model.Usuario;
import com.mascotav.usuario_service.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario buscarPorId(Integer id) {
        try {
            log.info("buscando usuario ...");
             return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        }catch(Exception e){
            log.error("No se pudo encontrar id: ", e);
            throw new RuntimeException("No se pudo encontrar el usuario", e);
        }
    }

    public UsuarioDTO registrarUsuario(Usuario usuario) {
        try{
            Usuario nuevo = usuarioRepository.save(usuario);
            log.info("usuario creado.");
            return convertirADTO(nuevo); 
        }catch(Exception e){
            log.error("No se pudo registrar el usuario: ", e);
            throw new RuntimeException("No se pudo registrar el usuario", e);
        }

    }

    private UsuarioDTO convertirADTO(Usuario usuario) {

        UsuarioDTO usuDTO = new UsuarioDTO();

        usuDTO.setIdUsuario(usuario.getId());
        usuDTO.setNombreUser(usuario.getNombreUsuario());

        return usuDTO;
    }
}
