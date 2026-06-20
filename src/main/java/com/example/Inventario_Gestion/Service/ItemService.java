package com.example.Inventario_Gestion.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Inventario_Gestion.DTO.AccionDTOExterno;
import com.example.Inventario_Gestion.DTO.ItemDTO;
import com.example.Inventario_Gestion.Model.Item;
import com.example.Inventario_Gestion.Repository.ItemRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    private final WebClient.Builder webClientBuilder;

    public ItemService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<ItemDTO> obtenerTodos() {
        return itemRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ItemDTO obtenerItemId(Integer id) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        return convertirADTO(item);
    }

    public ItemDTO guardar(Item item) {

        AccionDTOExterno accion = webClientBuilder.build()
                .get()
                .uri("http://accion-service/api/v1/accion/{id}", item.getIdAccionFk())
                .retrieve()
                .bodyToMono(AccionDTOExterno.class)
                .block();

        if (accion == null) {
            throw new RuntimeException("Acción no encontrada");
        }

        itemRepository.save(item);
        return convertirADTO(item);
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