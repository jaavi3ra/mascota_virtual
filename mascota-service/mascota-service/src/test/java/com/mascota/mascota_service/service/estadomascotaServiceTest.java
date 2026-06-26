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

@ExtendWith(MockitoExtension.class) //puerta de entrada para hacer pruebas inicializa Mockito
public class estadomascotaServiceTest {


    @Mock // crea un simulador, en este caso  del repositorio
    private EstadoMascotaRepository estadoMascotaRepository;

    @InjectMocks // crea la clase a testear y utliza el mock anterior
    private EstadoMascotaService estadoMascotaService;

    @Test
    void iniciarEstado_DeberiaCrearEstadoInicial() {

        // GIVEN = preparo los datos de entrada
        Mascota mascota = new Mascota();
        mascota.setNombre("efe");

        // WHEN = metodo que quiero testear
        EstadoMascota estado = estadoMascotaService.iniciarEstado(mascota);

        // THEN 
        assertNotNull(estado);
        assertEquals(100, estado.getHambre());
        assertEquals(100, estado.getEnergia());
        assertEquals(100, estado.getSalud());
        assertEquals(100, estado.getFelicidad());

        // verificar si realmente el metodo llama a .save del repositorio
        verify(estadoMascotaRepository).save(estado);
    }
}

