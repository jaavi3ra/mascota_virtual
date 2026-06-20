package com.mascota.mascota_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mascota.mascota_service.model.Nivel;
import com.mascota.mascota_service.repository.NivelRepository;

@ExtendWith(MockitoExtension.class)
public class nivelServiceTest {

    @Mock
    private NivelRepository nivelRepository;

    @InjectMocks
    private NivelService nivelService;

    @Test
    void iniciarNivel_DeberiaCrearNivelInicial() {

        // WHEN
        Nivel nivel = nivelService.iniciarNivel();

        // THEN
        assertNotNull(nivel);
        assertEquals(1, nivel.getNum_nivel());
        assertEquals(10, nivel.getExp_req());

        verify(nivelRepository, times(1)).save(any(Nivel.class));
    }

}

