package com.mascotav.usuario_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mascotav.usuario_service.model.Usuario;
import com.mascotav.usuario_service.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class usuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void buscarPorId_DeberiaRetornarUsuario() {

        // GIVEN
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombreUsuario("Fernanda");

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(usuario));

        // WHEN
        Usuario resultado = usuarioService.buscarPorId(1);

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Fernanda", resultado.getNombreUsuario());

        verify(usuarioRepository, times(1))
                .findById(1);
    }

    @Test
    void buscarPorId_DeberiaRetornarNullSiNoExiste() {

        // GIVEN
        when(usuarioRepository.findById(1))
                .thenReturn(Optional.empty());

        // WHEN
        Usuario resultado = usuarioService.buscarPorId(1);

        // THEN
        assertNull(resultado);

        verify(usuarioRepository, times(1))
                .findById(1);
    }
}

