package com.mascota.mascota_service.service;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.client.WebClient;
import com.mascota.mascota_service.repository.MascotaRepository;
import com.mascota.mascota_service.repository.TipoMascotaRepository;


@SpringBootTest
public class mascotaServiceTest {
    @MockitoBean
    private TipoMascotaRepository tipoMascotaRepository;
    @@MockitoBean
    private MascotaRepository mascotaRepository;
    @Autowired
    private EstadoMascotaService estadoMascotaService;
    @Autowired
    private NivelService nivelService;
    @Autowired
    private WebClient.Builder webClientBuilder;


}
