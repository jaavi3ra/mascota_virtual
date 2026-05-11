package com.example.mascotav.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Integer idMascota;

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Size(min = 4, max = 10, message = "El nombre debe tener al menos 4 caracteres")
    @Column(length = 10, nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "exp_actual", nullable = false)
    private Integer expActual;

    //@Builder.Default
    //@Min(value = 1, message = "El nivel minimo es 1")
    //@Max(value = 99, message = "El nivel maximo es 99")
    //@Column(nullable = false)
    //private Integer nivel = 0;

    // --- RELACIÓN ---

    // --------------DEFINICIONES--------------

    // @FetchType.LAZY ;
    // Carga relaciones solo cuando se accede a ellas.
    // Ejemplo: mascota.getUsuario() ejecuta la consulta recién en ese momento.

    // @EqualsAndHashCode.Exclude ;
    // Evita incluir relaciones en los métodos equals() y hashCode().
    // Ejemplo: al comparar dos Mascota, no se evalúa usuario para evitar recursión
    // o problemas con LAZY.

    // Exclude en toString:
    // No imprimir relaciones (evita loops y logs gigantes).

    @ManyToOne
    @JoinColumn(name = "id_tipo_mascota_fk")
    private TipoMascota tipoMascota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_FK", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nivel_FK")
    @EqualsAndHashCode.Exclude
    private Nivel nivel;

    @OneToOne(mappedBy = "mascota", fetch = FetchType.LAZY)
    private EstadoMascota estadoMascota;

    @OneToMany(mappedBy = "mascota", fetch = FetchType.LAZY)
    private List<HistorialAcciones> historialAcciones;

}
