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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public ItemDTO obtenerItemId(Integer id) {
        try{
            Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));
                log.info("id obtenido.");
            return convertirADTO(item);
        }catch(Exception e){
            log.error("error [getItem]: ", e );
            return null;
        }

    }

    public ItemDTO guardar(Item item) {
        try{
            itemRepository.save(item);
            log.info("item guardado.");
            return convertirADTO(item);
        }catch(Exception e ){
            log.error("error [saveItem]: ", e);
            return null;
        }

    }

    private ItemDTO convertirADTO(Item item) {

        ItemDTO itDTO = new ItemDTO();

        itDTO.setIdItem(item.getIdItem());
        itDTO.setNombreItem(item.getNombreItem());
        itDTO.setTipoItem(item.getTipoItem());
        itDTO.setIdAccionFk(item.getIdAccionFk());

        List<String> nombresItem = new ArrayList<>();
        nombresItem.add(item.getNombreItem());
        itDTO.setItems_nombres(nombresItem);

        List<String> itemsComprados = new ArrayList<>();
        itDTO.setItems_comprados(itemsComprados);

        return itDTO;
    }
}
