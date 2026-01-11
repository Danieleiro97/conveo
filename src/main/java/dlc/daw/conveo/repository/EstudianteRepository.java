package dlc.daw.conveo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dlc.daw.conveo.model.Estudiante;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    List<Estudiante> findByTutorEmpresa_Id(Long tutorEmpresaId);

    long countByTutorEmpresa_IdAndActivoTrue(Long tutorEmpresaId);

    @Modifying
    @Transactional
    @Query("UPDATE Estudiante e SET e.tutorEmpresa = null WHERE e.tutorEmpresa.id = :tutorId")
    int desasignarTutorEmpresa(@Param("tutorId") Long tutorId);

}