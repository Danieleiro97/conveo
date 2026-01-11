package dlc.daw.conveo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dlc.daw.conveo.model.TutorEmpresa;
import dlc.daw.conveo.repository.EstudianteRepository;
import dlc.daw.conveo.repository.TutorEmpresaRepository;
import dlc.daw.conveo.repository.UsuarioRepository;

@Service
public class TutorEmpresaService {

    private final TutorEmpresaRepository repo;
    private final EstudianteRepository estudianteRepository;
    private final UsuarioRepository usuarioRepository;
    private final AsignacionTutorEmpresaService asignacionTutorEmpresaService;

    public TutorEmpresaService(TutorEmpresaRepository repo,
            EstudianteRepository estudianteRepository,
            UsuarioRepository usuarioRepository,
            AsignacionTutorEmpresaService asignacionTutorEmpresaService) {
        this.repo = repo;
        this.estudianteRepository = estudianteRepository;
        this.usuarioRepository = usuarioRepository;
        this.asignacionTutorEmpresaService = asignacionTutorEmpresaService;
    }

    public List<TutorEmpresa> listarTodos() {
        return repo.findAll();
    }

    public TutorEmpresa buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public TutorEmpresa buscarPorEmailUsuario(String email) {
        return repo.findByUsuario_Email(email).orElse(null);
    }

    public void guardar(TutorEmpresa tutor) {
        repo.save(tutor);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public long eliminarTutorEmpresaDesasignandoEstudiantes(Long tutorId) {

        long activos = estudianteRepository.countByTutorEmpresa_IdAndActivoTrue(tutorId);

        // Desasigna el tutor de TODOS los estudiantes que lo tengan
        estudianteRepository.desasignarTutorEmpresa(tutorId);

        // Ahora ya no hay FK que impida borrar
        repo.deleteById(tutorId);

        return activos;
    }

    @Transactional
    public void eliminarSiNoTieneActivos(Long tutorId) {
        long activos = estudianteRepository.countByTutorEmpresa_IdAndActivoTrue(tutorId);
        if (activos > 0) {
            throw new IllegalStateException("NO_SE_PUEDE_BORRAR_TIENE_ACTIVOS:" + activos);
        }

        TutorEmpresa tutor = repo.findById(tutorId).orElse(null);
        if (tutor == null)
            return;

        // 1) borrar tutor
        repo.delete(tutor);

        // 2) borrar usuario asociado (si existe)
        if (tutor.getUsuario() != null) {
            usuarioRepository.deleteById(tutor.getUsuario().getId());
        }
    }

    @Transactional
    public void desactivarSiNoTieneAsignacionesActivas(Long tutorId) {

        long activas = asignacionTutorEmpresaService.contarAsignacionesActivasDelTutor(tutorId);
        if (activas > 0) {
            throw new IllegalStateException("NO_SE_PUEDE_DESACTIVAR_TIENE_ASIGNACIONES_ACTIVAS:" + activas);
        }

        TutorEmpresa tutor = repo.findById(tutorId).orElse(null);
        if (tutor == null)
            return;

        tutor.setActivo(false);
        tutor.setFechaBaja(LocalDate.now());
        repo.save(tutor);

        if (tutor.getUsuario() != null) {
            tutor.getUsuario().setActivo(false);
            usuarioRepository.save(tutor.getUsuario());
        }
    }

    @Transactional
    public void activarTutor(Long tutorId) {
        TutorEmpresa tutor = repo.findById(tutorId).orElse(null);
        if (tutor == null)
            return;

        tutor.setActivo(true);
        tutor.setFechaBaja(null);
        repo.save(tutor);

        if (tutor.getUsuario() != null) {
            tutor.getUsuario().setActivo(true);
            usuarioRepository.save(tutor.getUsuario());
        }
    }
}
