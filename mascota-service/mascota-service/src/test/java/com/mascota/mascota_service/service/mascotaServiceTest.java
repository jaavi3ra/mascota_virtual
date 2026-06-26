package com.mascota.mascota_service.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mascota.mascota_service.DTO.MascotaDTO;
import com.mascota.mascota_service.model.EstadoMascota;
import com.mascota.mascota_service.model.Mascota;
import com.mascota.mascota_service.model.Nivel;
import com.mascota.mascota_service.model.TipoMascota;
import com.mascota.mascota_service.repository.MascotaRepository;
import com.mascota.mascota_service.repository.TipoMascotaRepository;




@ExtendWith(MockitoExtension.class)
public class mascotaServiceTest {
    @Mock
    private TipoMascotaRepository tipoMascotaRepository;
    @Mock
    private MascotaRepository mascotaRepository;
    @Mock
    private EstadoMascotaService estadoMascotaService;
    @Mock
    private NivelService nivelService;
    @Mock
    private UsuarioClientService usuarioClientService;

    @InjectMocks
    private MascotaService mascotaService;

    @Test
    void crearMascota_DeberiaCrearMascotaCorrectamente() {

        // GIVEN
        Integer userId = 1;
        Integer tipoId = 1;
        String nombre = "Firulais";

        TipoMascota tipoMascota = new TipoMascota();
        tipoMascota.setId(tipoId);
        tipoMascota.setNombreTipoMascota("Dragon");

        Nivel nivel = new Nivel();
        nivel.setNum_nivel(1);
        nivel.setExp_req(10);

        EstadoMascota estado = new EstadoMascota();
        estado.setHambre(100);
        estado.setEnergia(100);
        estado.setFelicidad(100);
        estado.setSalud(100);

        // WHEN
        when(usuarioClientService.obtenerUsuario(userId))
                .thenReturn(userId);

        when(tipoMascotaRepository.findById(tipoId))
                .thenReturn(Optional.of(tipoMascota));

        when(nivelService.iniciarNivel())
                .thenReturn(nivel);

        when(estadoMascotaService.iniciarEstado(any(Mascota.class)))
                .thenReturn(estado);

        when(mascotaRepository.save(any(Mascota.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        MascotaDTO resultado =
                mascotaService.crearMascota(userId, nombre, tipoId);

        // THEN
        assertNotNull(resultado);
        assertEquals("Firulais", resultado.getNombre());
        assertEquals("Dragon", resultado.getTipoMascota());
        assertEquals(1, resultado.getNivelActual());

        verify(usuarioClientService).obtenerUsuario(userId);
        verify(tipoMascotaRepository).findById(tipoId);
        verify(nivelService).iniciarNivel();
        verify(estadoMascotaService).iniciarEstado(any(Mascota.class));

        //  Se guarda dos veces: al crear la mascota y al asignar el estado 
        verify(mascotaRepository, times(2))
                .save(any(Mascota.class));
    }
}
    

