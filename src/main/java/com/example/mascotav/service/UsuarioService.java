package com.example.mascotav.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.UsuarioDTO;
import com.example.mascotav.model.Usuario;
import com.example.mascotav.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired

    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

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

        if (usuario.getMascotas() != null) {
            usuDTO.setCantidadMascotas(usuario.getMascotas().size());
        } else {
            usuDTO.setCantidadMascotas(0);
        }

        return usuDTO;
    }

}
