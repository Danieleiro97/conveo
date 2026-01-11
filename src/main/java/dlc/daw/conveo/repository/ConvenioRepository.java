package dlc.daw.conveo.repository;

import dlc.daw.conveo.model.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ConvenioRepository extends JpaRepository<Convenio, Long> {

    List<Convenio> findByCentro_IdOrderByFechaInicioDesc(Long centroId);

    Optional<Convenio> findFirstByCentro_IdAndActivoTrue(Long centroId);

    Optional<Convenio> findFirstByCentro_IdAndActivoTrueAndIdNot(Long centroId, Long id);

}
