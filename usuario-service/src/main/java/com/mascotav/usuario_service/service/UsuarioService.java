package com.mascotav.usuario_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotav.usuario_service.dto.UsuarioDTO;
import com.mascotav.usuario_service.model.Usuario;
import com.mascotav.usuario_service.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirADTO(usuario);
    }

    public UsuarioDTO registrarUsuario(Usuario usuario) {
        Usuario nuevo = usuarioRepository.save(usuario);
        return convertirADTO(nuevo);
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {

        UsuarioDTO usuDTO = new UsuarioDTO();

        usuDTO.setIdUsuario(usuario.getId());
        usuDTO.setNombreUser(usuario.getNombreUsuario());

        return usuDTO;
    }
}
