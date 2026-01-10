package dlc.daw.conveo.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CentroTitulacionId implements Serializable {

    private Long centroId;
    private Long titulacionId;
}