package dlc.daw.conveo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import dlc.daw.conveo.model.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
  List<Estudiante> findByTutorEmpresa_Id(Long tutorEmpresaId);

  long countByTutorEmpresa_IdAndActivoTrue(Long tutorEmpresaId);

  @Modifying
  @Transactional
  @Query("UPDATE Estudiante e SET e.tutorEmpresa = null WHERE e.tutorEmpresa.id = :tutorId")
  int desasignarTutorEmpresa(@Param("tutorId") Long tutorId);

  @Query("""
          SELECT e
          FROM Estudiante e
          WHERE (:centroId IS NULL OR e.centro.id = :centroId)
            AND (:titulacionId IS NULL OR e.titulacion.id = :titulacionId)
            AND (:convenioId IS NULL OR e.convenio.id = :convenioId)
            AND (:activo IS NULL OR e.activo = :activo)
            AND (:tutorAsignado IS NULL OR
                  (:tutorAsignado = TRUE AND e.tutorEmpresa IS NOT NULL) OR
                  (:tutorAsignado = FALSE AND e.tutorEmpresa IS NULL)
                )
          ORDER BY e.apellidos, e.nombre
      """)
  List<Estudiante> buscarConFiltros(@Param("centroId") Long centroId,
      @Param("titulacionId") Long titulacionId,
      @Param("convenioId") Long convenioId,
      @Param("activo") Boolean activo,
      @Param("tutorAsignado") Boolean tutorAsignado);

  List<Estudiante> findByConvenio_IdOrderByApellidosAscNombreAsc(Long convenioId);

  List<Estudiante> findByCentro_IdOrderByApellidosAscNombreAsc(Long centroId);

  List<Estudiante> findByActivoTrueOrderByApellidosAscNombreAsc();

  List<Estudiante> findByActivoFalseOrderByApellidosAscNombreAsc();

  // Estudiantes activos, con tutor asignado, cuyas prácticas terminan en una
  // fecha exacta
  List<Estudiante> findByActivoTrueAndTutorEmpresaIsNotNullAndFechaFinPracticas(LocalDate fechaFinPracticas);
}