package com.mascota.mascota_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mascota.mascota_service.model.EstadoMascota;
import com.mascota.mascota_service.model.Mascota;
import com.mascota.mascota_service.repository.EstadoMascotaRepository;

@ExtendWith(MockitoExtension.class)
public class estadomascotaServiceTest {


    @Mock
    private EstadoMascotaRepository estadoMascotaRepository;

    @InjectMocks
    private EstadoMascotaService estadoMascotaService;

    @Test
    void iniciarEstado_DeberiaCrearEstadoInicial() {

        // GIVEN
        Mascota mascota = new Mascota();
        mascota.setNombre("efe");

        // WHEN
        EstadoMascota estado = estadoMascotaService.iniciarEstado(mascota);

        // THEN
        assertNotNull(estado);
        assertEquals(100, estado.getHambre());
        assertEquals(100, estado.getEnergia());
        assertEquals(100, estado.getSalud());
        assertEquals(100, estado.getFelicidad());

        verify(estadoMascotaRepository).save(any(EstadoMascota.class));
    }
}

