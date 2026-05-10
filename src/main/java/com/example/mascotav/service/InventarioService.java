package com.example.mascotav.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mascotav.DTO.InventarioDTO;
import com.example.mascotav.model.Inventario;
import com.example.mascotav.repository.InventarioRepository;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    public List<InventarioDTO> listarItemdelInventario(Integer iduser){
        List<InventarioDTO> inventItem = new ArrayList<>();
        for(Inventario inv : inventarioRepository.findInventbyUsuario(iduser)){
            inventItem.add(convertirADTO(inv));
        }
        return inventItem;
    }




    private InventarioDTO convertirADTO (Inventario inv){
        InventarioDTO invDTO = new InventarioDTO();
        invDTO.setId_inven(inv.getId_inven());
        invDTO.setCantidad(inv.getCantidad());
        
        if(inv.getItem() != null){
            invDTO.setItem((inv.getItem()).getIdItem());

        if(inv.getUsuario() != null){
            invDTO.setUsuario((inv.getUsuario()).getId());
        }
        return invDTO;
    }
    
}
