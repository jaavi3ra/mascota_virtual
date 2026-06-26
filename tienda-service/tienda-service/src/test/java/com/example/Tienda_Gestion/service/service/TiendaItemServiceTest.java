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

import com.example.Tienda_Gestion.DTO.ItemDTOExterno;
import com.example.Tienda_Gestion.DTO.InventarioDTOExterno;
import com.example.Tienda_Gestion.DTO.TiendaItemDTO;
import com.example.Tienda_Gestion.DTO.UsuarioDTOExterno;
import com.example.Tienda_Gestion.Model.Tienda;
import com.example.Tienda_Gestion.Model.TiendaItem;
import com.example.Tienda_Gestion.Repository.TiendaItemRepository;
import com.example.Tienda_Gestion.Repository.TiendaRepository;
import com.example.Tienda_Gestion.Service.TiendaItemService;
import com.example.Tienda_Gestion.Service.Client.ItemClientService;
import com.example.Tienda_Gestion.Service.Client.InventarioClientService;
import com.example.Tienda_Gestion.Service.Client.UsuarioClientService;

@ExtendWith(MockitoExtension.class)
public class TiendaItemServiceTest {

    @Mock
    private TiendaRepository tiendaRepository;

    @Mock
    private ItemClientService itemClientService;

    @Mock
    private UsuarioClientService usuarioClientService;

    @Mock
    private TiendaItemRepository tiendaItemRepository;

    @Mock
    private InventarioClientService inventarioClientService;

    @InjectMocks
    private TiendaItemService tiendaItemService;

    @Test
    void agregarItemATienda() {
        //GIVEN
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

        //WHEN
        when(itemClientService.obtenerItem(5)).thenReturn(itemDTOExternoFalso);

        when(tiendaRepository.findById(idTSimulado)).thenReturn(Optional.of(tiendaFalsa));

        when(tiendaItemRepository.save(any(TiendaItem.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        TiendaItemDTO resultado = tiendaItemService.agregarItemATienda(tiendaItemFalso);

        //THEN
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals(5, resultado.getId_item_FK(), "El ID del ítem debe coincidir");

        verify(tiendaRepository).findById(idTSimulado);
        verify(itemClientService).obtenerItem(5);
        verify(tiendaItemRepository).save(any(TiendaItem.class));
    }

    @Test
    void comprarItem() {
        //GIVEN
        TiendaItem tiendaItemFalso = new TiendaItem();
        tiendaItemFalso.setIdTiendaItem(3);
        tiendaItemFalso.setIdItemFk(5);

        ItemDTOExterno itemDTOExternoFalso = new ItemDTOExterno();
        itemDTOExternoFalso.setIdItem(5);
        itemDTOExternoFalso.setTipoItem("Potenciador de ataque");

        UsuarioDTOExterno usuarioDTOExternoFalso = new UsuarioDTOExterno();
        usuarioDTOExternoFalso.setIdUsuario(3);
        usuarioDTOExternoFalso.setNombreUser("Lord Valdomero");

        //WHEN
        when(itemClientService.obtenerItem(5)).thenReturn(itemDTOExternoFalso);
        when(usuarioClientService.obtenerUsuario(3)).thenReturn(usuarioDTOExternoFalso);

        when(tiendaItemRepository.findById(3)).thenReturn(Optional.of(tiendaItemFalso));
        when(inventarioClientService.findByUsuarioAndItem(3, 5)).thenReturn(null);
        when(inventarioClientService.guardarInventario(any(InventarioDTOExterno.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        assertDoesNotThrow(() -> tiendaItemService.comprarItem(3, 3));

        //THEN
        verify(tiendaItemRepository).findById(3);
        verify(itemClientService).obtenerItem(5);
        verify(usuarioClientService).obtenerUsuario(3);
        verify(inventarioClientService).guardarInventario(any(InventarioDTOExterno.class));
    }
}
