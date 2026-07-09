package com.accion.accion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import com.accion.accion.DTO.ItemDTOExterno;
import com.accion.accion.DTO.MascotaDTOExterno;
import com.accion.accion.model.Accion;
import com.accion.accion.repository.AccionRepository;
import com.accion.accion.repository.HistorialAccionesRepository;
import com.accion.accion.service.AccionService;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class AccionApplicationTests {

    @Mock
    private AccionRepository accionRepository;

    @Mock
    private HistorialAccionesRepository historialAccionesRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    @SuppressWarnings("rawtypes")
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    @SuppressWarnings("rawtypes")
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private AccionService accionService;

    private Accion accionMock;

    @BeforeEach
    void setUp() {
        accionMock = new Accion();
        accionMock.setIdAccion(1);
        accionMock.setNombreAccion("Alimentar");
    }

    @Test
    void testBuscarPorIdExitoso() {
        when(accionRepository.findById(1)).thenReturn(Optional.of(accionMock));

        Accion resultado = accionService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals("Alimentar", resultado.getNombreAccion());
        verify(accionRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorIdNoEncontrado() {
        when(accionRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            accionService.buscarPorId(99);
        });

        assertEquals("Accion con ID 99 no encontrada.", exception.getMessage());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testEjecutarAccionDeItemExitoso() {
        ItemDTOExterno itemDTO = new ItemDTOExterno();
        itemDTO.setIdItem(10);
        itemDTO.setNombreItem("Manzana");
        itemDTO.setIdAccion(1);

        MascotaDTOExterno mascotaDTO = new MascotaDTOExterno();
        mascotaDTO.setIdMascota(5);
        mascotaDTO.setNombre("Puchini");

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ItemDTOExterno.class)).thenReturn(Mono.just(itemDTO));
        when(responseSpec.bodyToMono(MascotaDTOExterno.class)).thenReturn(Mono.just(mascotaDTO));

        when(accionRepository.findById(1)).thenReturn(Optional.of(accionMock));

        String resultado = accionService.ejecutarAccionDeItem(5, 10);

        assertNotNull(resultado);
        assertTrue(resultado.contains("Manzana"));
        assertTrue(resultado.contains("Alimentar"));
        verify(historialAccionesRepository, times(1)).save(any());
    }
}
