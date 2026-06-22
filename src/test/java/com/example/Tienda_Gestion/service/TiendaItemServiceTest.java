package com.example.Tienda_Gestion.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Tienda_Gestion.DTO.ItemDTOExterno;
import com.example.Tienda_Gestion.DTO.TiendaItemDTO;
import com.example.Tienda_Gestion.DTO.UsuarioDTOExterno;
import com.example.Tienda_Gestion.Model.Tienda;
import com.example.Tienda_Gestion.Model.TiendaItem;
import com.example.Tienda_Gestion.Repository.TiendaItemRepository;
import com.example.Tienda_Gestion.Repository.TiendaRepository;
import com.example.Tienda_Gestion.Service.TiendaItemService;

@ExtendWith(MockitoExtension.class)
public class TiendaItemServiceTest {

    @Mock
    private TiendaRepository tiendaRepository;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient.Builder webClientBuilder;

    @Mock
    private TiendaItemRepository tiendaItemRepository;

    @InjectMocks
    private TiendaItemService tiendaItemService;

    @Test
    void agregarItemATienda() {

        Integer idTSimulado = 3;

        Tienda tiendaFalsa = new Tienda();
        tiendaFalsa.setIdTienda(idTSimulado);
        tiendaFalsa.setNombreTienda("Hydra");

        TiendaItem tiendaItemFalso = new TiendaItem();
        tiendaItemFalso.setTienda(tiendaFalsa);
        tiendaItemFalso.setIdItemFk(5);

        ItemDTOExterno itemDTOExternoFalso = new ItemDTOExterno();
        itemDTOExternoFalso.setIdItem(5);
        itemDTOExternoFalso.setNombreItem("Hongos alucinogenos de poder");
        itemDTOExternoFalso.setTipoItem("Potenciador de ataque");

        when(webClientBuilder.build()
                .get()
                .uri("http://inventario_gestion-service/api/v1/item/{id}", 5)
                .retrieve()
                .bodyToMono(ItemDTOExterno.class)
                .block())
                .thenReturn(itemDTOExternoFalso);

        when(tiendaRepository.findById(idTSimulado)).thenReturn(Optional.of(tiendaFalsa));

        when(tiendaItemRepository.save(any(TiendaItem.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        TiendaItemDTO resultado = tiendaItemService.agregarItemATienda(tiendaItemFalso);

        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals(5, resultado.getId_item_FK(), "El ID del ítem debe coincidir");

        verify(tiendaRepository).findById(idTSimulado);
        verify(tiendaItemRepository).save(any(TiendaItem.class));
    }

    @Test
    void comprarItem() {

        TiendaItem tiendaItemFalso = new TiendaItem();
        tiendaItemFalso.setIdTiendaItem(3);
        tiendaItemFalso.setIdItemFk(5);
        tiendaItemFalso.setCooldownSegundos(180);

        ItemDTOExterno itemDTOExternoFalso = new ItemDTOExterno();
        itemDTOExternoFalso.setIdItem(5);
        itemDTOExternoFalso.setTipoItem("Potenciador de ataque");

        UsuarioDTOExterno usuarioDTOExternoFalso = new UsuarioDTOExterno();
        usuarioDTOExternoFalso.setIdUsuario(3);
        usuarioDTOExternoFalso.setNombreUser("Lord Valdomero");

        when(webClientBuilder.build()
                .get()
                .uri("http://inventario_gestion-service/api/v1/item/{id}", 5)
                .retrieve()
                .bodyToMono(ItemDTOExterno.class)
                .block())
                .thenReturn(itemDTOExternoFalso);

        when(webClientBuilder.build()
                .get()
                .uri("http://usuario-service/api/v1/usuario/buscar-iduser/{iduser}", 3)
                .retrieve()
                .bodyToMono(UsuarioDTOExterno.class)
                .block())
                .thenReturn(usuarioDTOExternoFalso);

        when(tiendaItemRepository.findById(3)).thenReturn(Optional.of(tiendaItemFalso));

        assertDoesNotThrow(() -> tiendaItemService.comprarItem(3, 3));

        verify(tiendaItemRepository).findById(3);
    }
}
