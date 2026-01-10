package dlc.daw.conveo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "centro_titulacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CentroTitulacion {

    @EmbeddedId
    private CentroTitulacionId id;

    @ManyToOne
    @MapsId("centroId")
    @JoinColumn(name = "centro_id")
    private Centro centro;

    @ManyToOne
    @MapsId("titulacionId")
    @JoinColumn(name = "titulacion_id")
    private Titulacion titulacion;
}
