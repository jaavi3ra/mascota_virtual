package com.example.Inventario_Gestion.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Inventario_Gestion.DTO.AccionDTOExterno;
import com.example.Inventario_Gestion.DTO.MascotaDTOExterno;
import com.example.Inventario_Gestion.Model.Inventario;
import com.example.Inventario_Gestion.Model.Item;
import com.example.Inventario_Gestion.Repository.InventarioRepository;
import com.example.Inventario_Gestion.Repository.ItemRepository;
import com.example.Inventario_Gestion.Service.AccionClientService;
import com.example.Inventario_Gestion.Service.InventarioService;
import com.example.Inventario_Gestion.Service.MascotaClientService;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @InjectMocks
    private InventarioService inventarioService;

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private MascotaClientService mascotaClientService;

    @Mock
    private AccionClientService accionClientService;

    @Test
    public void usarItem_DeberiaConsumirItemYRegistrarHistorial() {

            // Arrange
            Integer idMascota = 1;
            Integer idItem = 10;

            MascotaDTOExterno mascota = new MascotaDTOExterno();
            mascota.setNombre("efe");
            mascota.setIdUsuarioFk(5);

            Item item = new Item();
            item.setIdItem(idItem);
            item.setNombreItem("Manzana");
            item.setIdAccionFk(2);

            Inventario inventario = new Inventario();
            inventario.setCantidad(3);

            AccionDTOExterno accion = new AccionDTOExterno();
            accion.setNombreAccion("Comer");

            when(mascotaClientService.obtenerMascota(idMascota))
                    .thenReturn(mascota);

            when(itemRepository.findById(idItem))
                    .thenReturn(Optional.of(item));

            when(inventarioRepository.findByUsuarioAndItem(5, idItem))
                    .thenReturn(Optional.of(inventario));

            when(accionClientService.obtenerAccion(2))
                    .thenReturn(accion);

            // Act
            String resultado = inventarioService
                .usarItem(idMascota, idItem);

            // Assert
            assertNotNull(resultado);
            assertTrue(resultado.contains("efe"));

            verify(inventarioRepository).save(inventario);
            verify(mascotaClientService).aplicarEfectos(mascota, item);
            verify(accionClientService)
                    .registroHistorial(eq(idMascota), eq(accion), anyString());
        }

        @Test
        public void obtenerItemDelInventario_DeberiaRetornarNull_SiNoExiste() {

            Integer idUser = 10;
            Integer idItem = 5;

            when(inventarioRepository.findByUsuarioAndItem(idUser, idItem))
                    .thenReturn(Optional.empty());

            Inventario resultado =
                    inventarioService.obtenerItemdelInventario(idUser, idItem);

            assertNull(resultado);

            verify(inventarioRepository)
                    .findByUsuarioAndItem(idUser, idItem);
        }
}
