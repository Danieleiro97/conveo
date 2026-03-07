package dlc.daw.conveo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dlc.daw.conveo.model.SeguimientoTutor;

public interface SeguimientoTutorRepository extends JpaRepository<SeguimientoTutor, Long> {

    List<SeguimientoTutor> findByEstudiante_IdOrderByFechaDesc(Long estudianteId);

    List<SeguimientoTutor> findByTutorEmpresa_IdAndEstudiante_IdOrderByFechaDesc(
            Long tutorId, Long estudianteId);
}
