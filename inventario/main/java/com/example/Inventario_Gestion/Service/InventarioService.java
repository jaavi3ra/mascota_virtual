package com.example.mascotav.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.InventarioDTO;
import com.example.mascotav.model.Inventario;
import com.example.mascotav.model.Item;
import com.example.mascotav.model.Mascota;
import com.example.mascotav.repository.InventarioRepository;
import com.example.mascotav.repository.ItemRepository;
import com.example.mascotav.repository.MascotaRepository;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private MascotaService mascotaService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private HistorialAccionesService historialAccionesService;
    @Autowired
    private EvolucionService evolucionService;
    @Autowired
    private EstadoMascotaService estadoserService;

    public List<InventarioDTO> listarItemdelInventario(Integer iduser) {
        List<InventarioDTO> inventItem = new ArrayList<>();
        for (Inventario inv : inventarioRepository.findInventbyUsuario(iduser)) {
            inventItem.add(convertirADTO(inv));
        }
        return inventItem;
    }

    private Mascota obtenerMascota(Integer idmascota) {
        return mascotaRepository.findById(idmascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
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

    private void aplicarEfectos(Mascota mascota, Item item) {
        mascota.setExpActual(mascota.getExpActual() + item.getAccion().getAfectaExpBase());
        // guardar cambios de mascota experiencia y estado mascota
        mascotaRepository.save(mascota);
        estadoserService.editarEstado(mascota, item);
        // se crea un registro de la accion
        historialAccionesService.registrarHistorial(mascota, item);
    }

    // al usar item implica editar estado de mascota e inventario, ya que se consume
    // el item del inventario
    public String usarItem(Integer idMascota, Integer idItem) {

        Mascota mascota = obtenerMascota(idMascota);
        Item item = obtenerItem(idItem);
        Inventario inventario = obtenerItemdelInventario(mascota.getUsuario().getId(), idItem);

        validarStock(inventario);
        consumirItem(inventario);
        aplicarEfectos(mascota, item);

        return contruirMensaje(mascota, item);
    }

    private String contruirMensaje(Mascota mascota, Item item) {

        return " La Mascota: " + mascota.getNombre() +
                "\n\tUsó el item :" + item.getNombreItem() +
                "\n\tActivó la acción :" + item.getAccion().getNombreAccion() +
                "\n\t" + mascotaService.validarSubirDeNivel(mascota)
                + "\n\t" + evolucionService.verificarEvolucion(mascota);

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
