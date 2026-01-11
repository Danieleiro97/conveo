package dlc.daw.conveo.repository;

import dlc.daw.conveo.model.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ConvenioRepository extends JpaRepository<Convenio, Long> {

    Optional<Convenio> findFirstByCentro_IdAndActivoTrue(Long centroId);

    Optional<Convenio> findFirstByCentro_IdAndActivoTrueAndIdNot(Long centroId, Long id);

}
