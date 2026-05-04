<<<<<<< HEAD
package com.example.mascotav.model;

public class Mascota {

=======

package com.example.mascotav.Model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Integer id;

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Size(min = 4, max = 10, message = "El nombre debe tener al menos 4 caracteres")
    @Column(length = 10, nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "exp_actual", nullable = false)
    private Integer exp_actual;

    @Builder.Default
    @Min(value = 1, message = "El nivel minimo es 1")
    @Max(value = 99, message = "El nivel maximo es 99")
    @Column(nullable = false)
    private Integer nivel = 1;

    // Relaciones

    @ManyToOne(fetch = FetchType.EAGER) // OPCIONAL
    @JoinColumn(name = "id_tipo_mascota_FK", nullable = false)
    private TipoMascota tipoMascota;

    @ManyToOne(fetch = FetchType.LAZY) // OPCIONAL
    @JoinColumn(name = "id_user_FK", nullable = false)
    @ToString.Exclude
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER) // OPCIONAL
    @JoinColumn(name = "id_nivel_FK")
    private Nivel nivelEntidad; // Relación con la tabla 'nivel' del diagrama

    @OneToOne(mappedBy = "mascota", cascade = CascadeType.ALL)
    private EstadoMascota estado; // Relación con 'estado_mascota'

>>>>>>> 998d38d209692e1589776cc6235be063b49430c2
}
