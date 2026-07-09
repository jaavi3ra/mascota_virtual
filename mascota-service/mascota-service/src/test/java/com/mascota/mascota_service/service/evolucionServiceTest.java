package com.mascota.mascota_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mascota.mascota_service.model.Evolucion;
import com.mascota.mascota_service.model.Mascota;
import com.mascota.mascota_service.model.Nivel;
import com.mascota.mascota_service.model.TipoMascota;
import com.mascota.mascota_service.repository.EvolucionRepository;

@ExtendWith(MockitoExtension.class)
public class evolucionServiceTest {

    @Mock
    private EvolucionRepository evolucionRepository;

    @InjectMocks
    private EvolucionService evolucionService;

    @Test
    void verificarEvolucion_DeberiaEvolucionar() {

        //GIVEN
        TipoMascota tipo = new TipoMascota();

        Nivel nivel = new Nivel();
        nivel.setNum_nivel(5);

        Mascota mascota = new Mascota();
        mascota.setNombre("Dragon");
        mascota.setNivel(nivel);
        mascota.setTipoMascota(tipo);

        Evolucion evolucion = new Evolucion();
        evolucion.setNivel(5);

        //WHEN
        when(evolucionRepository.findByTipoMascota(tipo))
                .thenReturn(Optional.of(evolucion));

        String mensaje = evolucionService.verificarEvolucion(mascota);

        //THEN
        assertEquals("Tu mascota Dragon Evolucionó!", mensaje);

        verify(evolucionRepository).findByTipoMascota(tipo);
    }

}

