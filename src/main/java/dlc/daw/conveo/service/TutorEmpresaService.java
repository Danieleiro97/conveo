package dlc.daw.conveo.service;

import dlc.daw.conveo.model.TutorEmpresa;
import dlc.daw.conveo.repository.TutorEmpresaRepository;
import org.springframework.stereotype.Service;
import dlc.daw.conveo.repository.EstudianteRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TutorEmpresaService {

    private final TutorEmpresaRepository repo;
    private final EstudianteRepository estudianteRepository;

    public TutorEmpresaService(TutorEmpresaRepository repo, EstudianteRepository estudianteRepository) {
        this.repo = repo;
        this.estudianteRepository = estudianteRepository;
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

}
