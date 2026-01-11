package dlc.daw.conveo.service;

import dlc.daw.conveo.model.Estudiante;
import dlc.daw.conveo.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public List<Estudiante> listarTodos() {
        return estudianteRepository.findAll();
    }

    public void guardar(Estudiante estudiante) {
        estudianteRepository.save(estudiante);
    }

    public void eliminar(Long id) {
        estudianteRepository.deleteById(id);
    }

    public Estudiante buscarPorId(Long id) {
        return estudianteRepository.findById(id).orElse(null);
    }

    public List<Estudiante> listarPorTutorEmpresa(Long tutorEmpresaId) {
        return estudianteRepository.findByTutorEmpresa_Id(tutorEmpresaId);
    }

}
