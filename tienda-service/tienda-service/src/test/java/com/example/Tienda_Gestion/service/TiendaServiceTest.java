package com.example.Tienda_Gestion.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Tienda_Gestion.DTO.TiendaDTO;
import com.example.Tienda_Gestion.Model.Tienda;
import com.example.Tienda_Gestion.Repository.TiendaRepository;
import com.example.Tienda_Gestion.Service.TiendaService;

@ExtendWith(MockitoExtension.class)
public class TiendaServiceTest {

    @Mock
    private TiendaRepository tiendaRepository;

    @InjectMocks
    private TiendaService tiendaService;

    @Test
    void guardarTienda() {

        Integer idTSimulado = 2;

        Tienda tiendaFalsa = new Tienda();
        tiendaFalsa.setIdTienda(idTSimulado);
        tiendaFalsa.setNombreTienda("Jalato Real");

        when(tiendaRepository.save(tiendaFalsa)).thenReturn(tiendaFalsa);

        Tienda resultado = tiendaService.guardarTienda(tiendaFalsa);

        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals(idTSimulado, resultado.getIdTienda(), "El ID de la tienda debe coincidir");

        verify(tiendaRepository).save(any(Tienda.class));
    }

    @Test
    void obtenerPorId() {

        Integer idTSimulado = 2;

        Tienda tiendaFalsa = new Tienda();
        tiendaFalsa.setIdTienda(idTSimulado);
        tiendaFalsa.setNombreTienda("Jalato Real");

        when(tiendaRepository.findById(idTSimulado)).thenReturn(Optional.of(tiendaFalsa));

        TiendaDTO resultado = tiendaService.obtenerPorId(idTSimulado);

        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals(idTSimulado, resultado.getIdTienda(), "El ID de Tienda debe coincidir");

        verify(tiendaRepository).findById(idTSimulado);
    }

    @Test
    void actualizarNombreTienda() {

        Integer idTSimulado = 2;

        Tienda tiendaFalsa = new Tienda();
        tiendaFalsa.setIdTienda(idTSimulado);
        tiendaFalsa.setNombreTienda("Jalato Real");

        when(tiendaRepository.findById(idTSimulado)).thenReturn(Optional.of(tiendaFalsa));

        when(tiendaRepository.save(any(Tienda.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        TiendaDTO resultado = tiendaService.actualizarNombreTienda(idTSimulado, "Draco Quesos");

        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals("Draco Quesos", resultado.getNombreTienda(), "El Nombre de Tienda debe coincidir");

        verify(tiendaRepository).findById(idTSimulado);
        verify(tiendaRepository).save(tiendaFalsa);
    }

}
