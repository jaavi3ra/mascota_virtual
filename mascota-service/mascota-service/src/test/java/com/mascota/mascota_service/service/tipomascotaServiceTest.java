package com.mascota.mascota_service.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mascota.mascota_service.DTO.TipoMascotaDTO;
import com.mascota.mascota_service.model.TipoMascota;
import com.mascota.mascota_service.repository.TipoMascotaRepository;

@ExtendWith(MockitoExtension.class)
public class tipomascotaServiceTest {

    @Mock
    private TipoMascotaRepository tipoMascotaRepository;

    @InjectMocks
    private TipoMascotaService tipoMascotaService;

    @Test
    void creartipo_DeberiaGuardarCincoTipos() {

        when(tipoMascotaRepository.findAll())
                .thenReturn(List.of(
                        new TipoMascota(),
                        new TipoMascota(),
                        new TipoMascota(),
                        new TipoMascota(),
                        new TipoMascota()
                ));

        List<TipoMascotaDTO> lista = tipoMascotaService.creartipo();

        assertNotNull(lista);

        verify(tipoMascotaRepository, times(5))
                .save(any(TipoMascota.class));
    }


}
