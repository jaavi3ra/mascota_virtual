package com.example.Inventario_Gestion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Inventario_Gestion.DTO.ItemDTO;
import com.example.Inventario_Gestion.Model.Item;
import com.example.Inventario_Gestion.Repository.ItemRepository;
import com.example.Inventario_Gestion.Service.ItemService;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;


    @Test
    public void obtenerItemId_DeberiaRetornarItem() {

        Integer id = 1;

        Item item = new Item();
        item.setIdItem(id);
        item.setNombreItem("Manzana");
        item.setTipoItem("Comida");

        when(itemRepository.findById(id))
                .thenReturn(Optional.of(item));

        ItemDTO resultado = itemService.obtenerItemId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdItem());
        assertEquals("Manzana", resultado.getNombreItem());

        verify(itemRepository).findById(id);
    }

    @Test
    public void guardar_DeberiaRetornarItem() {

        Item item = new Item();
        item.setIdItem(1);
        item.setNombreItem("Manzana");
        item.setTipoItem("Comida");

        when(itemRepository.save(any(Item.class)))
                .thenReturn(item);

        ItemDTO resultado = itemService.guardar(item);

        assertNotNull(resultado);
        assertEquals("Manzana", resultado.getNombreItem());

        verify(itemRepository).save(item);
    }
}
