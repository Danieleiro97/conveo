package dlc.daw.conveo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "titulaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Titulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Nivel nivel;

    public enum Nivel {
        CFGM,
        CFGS,
        GRADO,
        MASTER,
        CURSO_ESPECIALIZACION
    }
}
