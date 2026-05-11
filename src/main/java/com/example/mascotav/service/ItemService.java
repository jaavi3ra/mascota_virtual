package com.example.mascotav.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.ItemDTO;
import com.example.mascotav.model.Inventario;
import com.example.mascotav.model.Item;
import com.example.mascotav.model.Mascota;
import com.example.mascotav.repository.InventarioRepository;
import com.example.mascotav.repository.ItemRepository;
import com.example.mascotav.repository.MascotaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private AccionService accionService;

    public String darItem(Integer userId, Integer  itemId, Integer idMascota) {
        // Buscar el item en el inventario
        Inventario inventario = inventarioRepository.findByUsuarioAndItem(userId, itemId)
                    .orElseThrow(() -> new RuntimeException("No tienes este item en tu inventario"));
        if(inventario.getCantidad() <= 0) {
            throw new RuntimeException("No te quedan items");
        }
        // Buscar mascota
        Mascota mascota = mascotaRepository.findById(idMascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        // Se ejecuta la accion
        String resultadoAccion = accionService.ejecutarAccionDeItem(mascota, inventario.getItem());
        
        // Restar al inventario
        inventario.setCantidad(inventario.getCantidad() - 1);
        
        // Se verifica si la cantidad es 0, si es asi lo borra
        if(inventario.getCantidad() == 0) {
            inventarioRepository.delete(inventario);
        }else {
            // Si es mayor a 0 lo guarda y se actualiza
            inventarioRepository.save(inventario);
        }

        return resultadoAccion;

    }

    public List<ItemDTO> obtenerTodos() {
        return itemRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Item guardar(Item item) {
        return itemRepository.save(item);
    }

    public void eliminar(Integer id) {
        if(!itemRepository.existsById(id)) {
            throw new RuntimeException("Error el item no existe");
        }

        try {
            itemRepository.deleteById(id);
        }catch (Exception e) {
            throw new RuntimeException("No se puede eliminar el item");
        }
    }

    private ItemDTO convertirADTO(Item item) {
        ItemDTO itDTO = new ItemDTO();
        itDTO.setIdItem(item.getIdItem());
        itDTO.setNombreItem(item.getNombreItem());
        itDTO.setTipoItem(item.getTipoItem());

        List<String> nombresItem = new ArrayList<>();
        nombresItem.add(item.getNombreItem());
        itDTO.setItems_nombres(nombresItem);

        List<String> itemsComprados = new ArrayList<>();
        itDTO.setItems_comprados(itemsComprados);

        return itDTO;
    }
}
