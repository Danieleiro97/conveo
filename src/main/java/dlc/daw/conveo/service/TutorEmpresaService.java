package dlc.daw.conveo.service;

import dlc.daw.conveo.model.TutorEmpresa;
import dlc.daw.conveo.repository.TutorEmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorEmpresaService {

    private final TutorEmpresaRepository repo;

    public TutorEmpresaService(TutorEmpresaRepository repo) {
        this.repo = repo;
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
}
