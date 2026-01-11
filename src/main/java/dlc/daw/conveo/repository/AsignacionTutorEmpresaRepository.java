package dlc.daw.conveo.repository;

import dlc.daw.conveo.model.AsignacionTutorEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AsignacionTutorEmpresaRepository extends JpaRepository<AsignacionTutorEmpresa, Long> {

    Optional<AsignacionTutorEmpresa> findFirstByEstudiante_IdAndFechaFinIsNull(Long estudianteId);

    List<AsignacionTutorEmpresa> findByEstudiante_IdOrderByFechaInicioDesc(Long estudianteId);

    long countByTutorEmpresa_IdAndFechaFinIsNull(Long tutorEmpresaId);
}
