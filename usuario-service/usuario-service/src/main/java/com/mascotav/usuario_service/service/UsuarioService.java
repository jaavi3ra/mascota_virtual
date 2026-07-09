package com.mascotav.usuario_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.mascotav.usuario_service.dto.UsuarioDTO;
import com.mascotav.usuario_service.model.Usuario;
import com.mascotav.usuario_service.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO buscarPorId(Integer id) {
        try {
            log.info("buscando usuario ...");
            Usuario usuario = usuarioRepository.findById(id).orElse(null);                        
            return convertirADTO(usuario);
                
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

    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {

        UsuarioDTO usuDTO = new UsuarioDTO();

        usuDTO.setIdUsuario(usuario.getId());
        usuDTO.setNombreUser(usuario.getNombreUsuario());

        return usuDTO;
    }
}
