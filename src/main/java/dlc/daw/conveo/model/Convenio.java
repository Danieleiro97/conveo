package dlc.daw.conveo.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "convenios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "centro_id", nullable = false)
    private Centro centro;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private boolean activo = true;
}
