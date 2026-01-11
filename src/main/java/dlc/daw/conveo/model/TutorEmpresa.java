package dlc.daw.conveo.model;

import jakarta.persistence.*;
import lombok.*;

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

    private boolean activo = true;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;
}
