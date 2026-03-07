package dlc.daw.conveo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dlc.daw.conveo.model.SeguimientoTutor;
import dlc.daw.conveo.repository.SeguimientoTutorRepository;

@Service
public class SeguimientoTutorService {

    private final SeguimientoTutorRepository repo;

    public SeguimientoTutorService(SeguimientoTutorRepository repo) {
        this.repo = repo;
    }

    public List<SeguimientoTutor> listarPorEstudiante(Long estudianteId) {
        return repo.findByEstudiante_IdOrderByFechaDesc(estudianteId);
    }

    public List<SeguimientoTutor> listarPorTutorYEstudiante(Long tutorId, Long estudianteId) {
        return repo.findByTutorEmpresa_IdAndEstudiante_IdOrderByFechaDesc(tutorId, estudianteId);
    }

    public void guardar(SeguimientoTutor s) {
        if (s.getValoracion() < 1 || s.getValoracion() > 5) {
            throw new IllegalArgumentException("La valoración debe estar entre 1 y 5.");
        }
        repo.save(s);
    }

    public SeguimientoTutor buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }
}