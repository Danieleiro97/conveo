package dlc.daw.conveo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "centros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Centro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String ubicacion;

    private String emailContacto;

    private boolean activo = true;

    private LocalDate fechaAlta = LocalDate.now();

    private LocalDate fechaBaja;
}
