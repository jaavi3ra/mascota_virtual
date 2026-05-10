package com.example.mascotav.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mascotav.DTO.ItemDTO;
import com.example.mascotav.model.Item;
import com.example.mascotav.repository.ItemRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<ItemDTO> obtenerTodos() {
        return itemRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Item guardar(Item item) {
        return itemRepository.save(item);
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
