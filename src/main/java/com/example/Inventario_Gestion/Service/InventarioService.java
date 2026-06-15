package com.example.Inventario_Gestion.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Inventario_Gestion.DTO.InventarioDTO;
import com.example.Inventario_Gestion.DTO.MascotaDTOExterno;
import com.example.Inventario_Gestion.Model.Inventario;
import com.example.Inventario_Gestion.Model.Item;
import com.example.Inventario_Gestion.Repository.InventarioRepository;
import com.example.Inventario_Gestion.Repository.ItemRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j

public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<InventarioDTO> listarItemdelInventario(Integer iduser) {
        List<InventarioDTO> inventItem = new ArrayList<>();
        for (Inventario inv : inventarioRepository.findInventbyUsuario(iduser)) {
            inventItem.add(convertirADTO(inv));
        }
        return inventItem;
    }

    private Item obtenerItem(Integer iditem) {
        return itemRepository.findById(iditem)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));
    }

    private Inventario obtenerItemdelInventario(Integer iduser, Integer iditem) {
        return inventarioRepository
                .findByUsuarioAndItem(iduser, iditem)
                .orElseThrow(() -> new RuntimeException("No tienes este item"));
    }

    private void validarStock(Inventario inventario) {
        if (inventario.getCantidad() <= 0) {
            throw new RuntimeException("No hay stock del item");
        }
    }

    private void consumirItem(Inventario inventario) {
        inventario.setCantidad(inventario.getCantidad() - 1);
        // guardar cambios del inventario
        inventarioRepository.save(inventario);
    }

    public String usarItem(MascotaDTOExterno mascotaDto, Integer idItem) {

        Item item = obtenerItem(idItem);

        Inventario inventario = obtenerItemdelInventario(mascotaDto.get, idItem);

        validarStock(inventario);
        consumirItem(inventario);

        return contruirMensaje(mascota, item);
    }

    private String contruirMensaje(MascotaDTOExterno mascotaDto, Item item) {

        return " La Mascota: " + mascotaDto.getNombre() +
                "\n\tUsó el item :" + item.getNombreItem() +
                "\n\tActivó la acción :" + item.getAccion().getNombreAccion();

    }

    private InventarioDTO convertirADTO(Inventario inv) {
        InventarioDTO invDTO = new InventarioDTO();
        invDTO.setId_inven(inv.getId_inven());
        invDTO.setCantidad(inv.getCantidad());

        if (inv.getItem() != null) {
            invDTO.setItem((inv.getItem()).getIdItem());
        }
        if (inv.getUsuario() != null) {
            invDTO.setUsuario((inv.getUsuario()).getId());
        }
        return invDTO;
    }
}
