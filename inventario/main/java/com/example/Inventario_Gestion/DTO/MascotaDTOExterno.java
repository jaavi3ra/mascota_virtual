import lombok.Data;

@Data
public class MascotaDTOExterno {

    private Integer idMascota;
    private EstadoMascotaDTO estado;
    private String nombre;
    private Integer nivelActual;

}
