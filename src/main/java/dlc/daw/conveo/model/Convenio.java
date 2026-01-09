package dlc.daw.conveo.model;
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

    private String centro;

    private String ubicacion;

    private boolean activo = true;
}
