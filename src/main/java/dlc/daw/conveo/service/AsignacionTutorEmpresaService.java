package dlc.daw.conveo.service;

import dlc.daw.conveo.model.AsignacionTutorEmpresa;
import dlc.daw.conveo.model.Estudiante;
import dlc.daw.conveo.model.TutorEmpresa;
import dlc.daw.conveo.repository.AsignacionTutorEmpresaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AsignacionTutorEmpresaService {

    private final AsignacionTutorEmpresaRepository repo;

    public AsignacionTutorEmpresaService(AsignacionTutorEmpresaRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void actualizarAsignacionTutor(Estudiante estudiante, TutorEmpresa nuevoTutor) {

        // 1) Cerrar asignación activa si existe
        var activaOpt = repo.findFirstByEstudiante_IdAndFechaFinIsNull(estudiante.getId());
        activaOpt.ifPresent(activa -> {
            // Si el tutor no cambia, no hacemos nada
            if (nuevoTutor != null && activa.getTutorEmpresa().getId().equals(nuevoTutor.getId())) {
                return;
            }
            activa.setFechaFin(LocalDate.now());
            repo.save(activa);
        });

        // 2) Si nuevoTutor es null, no creamos nueva
        if (nuevoTutor == null) {
            return;
        }

        // 3) Crear nueva asignación
        AsignacionTutorEmpresa nueva = new AsignacionTutorEmpresa();
        nueva.setEstudiante(estudiante);
        nueva.setTutorEmpresa(nuevoTutor);
        nueva.setFechaInicio(LocalDate.now());
        nueva.setFechaFin(null);
        repo.save(nueva);
    }

    public long contarAsignacionesActivasDelTutor(Long tutorId) {
        return repo.countByTutorEmpresa_IdAndFechaFinIsNull(tutorId);
    }

    public java.util.List<dlc.daw.conveo.model.AsignacionTutorEmpresa> historialPorEstudiante(Long estudianteId) {
    return repo.findByEstudiante_IdOrderByFechaInicioDesc(estudianteId);
}


}
