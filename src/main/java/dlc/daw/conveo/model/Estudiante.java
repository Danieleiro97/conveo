package dlc.daw.conveo.model;

import jakarta.persistence.*;
import lombok.*;

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

    // RELACIÓN MUCHOS A UNO - ESTUDIANTES CONVENIO
    @ManyToOne
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;
}