package dlc.daw.conveo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String dni;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaInicioPracticas;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaFinPracticas;

    private boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "centro_id", nullable = false)
    private Centro centro;

    @ManyToOne
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;
}
