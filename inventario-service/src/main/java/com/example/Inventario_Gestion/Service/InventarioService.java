package com.example.Inventario_Gestion.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Inventario_Gestion.DTO.AccionDTOExterno;
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

    @Autowired
    private MascotaClientService mascotaClientService;

    @Autowired
    private AccionClientService accionClientService;

    public Inventario obtenerInventariobyId(Integer id){
        try{
            return inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        }catch(Exception e){
            log.error("error [getInventario]: ", e);
            return  null;
        }
    }

    public Inventario guardarInventario(Inventario inventario){
        try{
            log.info("Guardando Inventario.");
            return inventarioRepository.save(inventario);
        }catch(Exception e){
            log.error("error [saveInventario]: ", e);
            return null;
        }
    }

    public List<InventarioDTO> listarItemdelInventario(Integer iduser) {
        try{
            List<InventarioDTO> inventItem = new ArrayList<>();
                for (Inventario inv : inventarioRepository.findInventbyUsuario(iduser)) {
                inventItem.add(convertirADTO(inv));
            }
            log.info("items de inventario listado.");
            return inventItem;
        }catch(Exception e ){
            log.error("error [listItems]: ", e);
            return null;
        }

    }

    public Item obtenerItem(Integer iditem) {
        try{
            return itemRepository.findById(iditem)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));
        }catch(Exception e){
            log.error("error [getItem]: ", e);
            return  null;
        }

    }

    public Inventario obtenerItemdelInventario(Integer iduser, Integer iditem) {
        try{
            return inventarioRepository
                .findByUsuarioAndItem(iduser, iditem)
                .orElseThrow(() -> new RuntimeException("No tienes este item"));
        }catch(Exception e ){
            log.error("error [getIteminInventario]: ", e);
            return null;
        }

    }

    private void validarStock(Inventario inventario) {
        if (inventario.getCantidad() <= 0) {
            throw new RuntimeException("No hay stock del item");
        }
    }

    private void consumirItem(Inventario inventario) {
        try{
            inventario.setCantidad(inventario.getCantidad() - 1);
            // guardar cambios del inventario
            inventarioRepository.save(inventario);
        }catch(Exception e){
            log.error("Error [consumirItem]: ", e);
        }

    }

    public String usarItem(Integer idMascota, Integer idItem) {
        try{
            log.info("Buscando mascota e Item...");
            // obtener objetos
            MascotaDTOExterno mascota = mascotaClientService
                .obtenerMascota(idMascota);

            Item item = obtenerItem(idItem);

            Inventario inventario = 
                obtenerItemdelInventario(mascota.getIdUsuarioFk(), idItem);

            AccionDTOExterno accion =  accionClientService
                .obtenerAccion(item.getIdAccionFk());
            // metodos validacion
            validarStock(inventario);
            consumirItem(inventario);
            mascotaClientService
                .aplicarEfectos(mascota, item);
            //registro de interaccion de mascota con item
            accionClientService
                .registroHistorial(idMascota, accion, contruirMensaje(mascota, item, accion));
            
            log.info("se uso el item en mascota correctamente.");
            return contruirMensaje(mascota, item, accion);            
        }catch(Exception e){
            log.error("error [useitem]: ", e);
            return null;
        }

    }

    private String contruirMensaje(MascotaDTOExterno mascotaDto, Item item, AccionDTOExterno accion) {

        return " La Mascota: " + mascotaDto.getNombre() +
                "\n\tUsó el item :" + item.getNombreItem() +
                "\n\tActivó la acción :" + accion.getNombreAccion();

    }

    private InventarioDTO convertirADTO(Inventario inv) {

        InventarioDTO invDTO = new InventarioDTO();
        invDTO.setId_inven(inv.getId_inven());
        invDTO.setCantidad(inv.getCantidad());

        if (inv.getItem() != null) {
            invDTO.setItem((inv.getItem()).getIdItem());
        }
        if (inv.getIdUserFk() != null) {
            invDTO.setUsuario((inv.getIdUserFk()));
        }
        return invDTO;
    }
}
