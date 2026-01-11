package dlc.daw.conveo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dlc.daw.conveo.model.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
List<Estudiante> findByTutorEmpresa_Id(Long tutorEmpresaId);
}