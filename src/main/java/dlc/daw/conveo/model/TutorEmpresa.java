package dlc.daw.conveo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tutores_empresa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutorEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String dni;

    private String cargoEmpresa;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaAlta = LocalDate.now();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaBaja;

    private boolean activo = true;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;
}
