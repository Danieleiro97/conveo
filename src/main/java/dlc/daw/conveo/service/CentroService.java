package dlc.daw.conveo.service;

import dlc.daw.conveo.model.Centro;
import dlc.daw.conveo.repository.CentroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentroService {

    private final CentroRepository centroRepository;

    public CentroService(CentroRepository centroRepository) {
        this.centroRepository = centroRepository;
    }

    public List<Centro> listarTodos() {
        return centroRepository.findAll();
    }

    public Centro buscarPorId(Long id) {
        return centroRepository.findById(id).orElse(null);
    }

    public void guardar(Centro centro) {
        centroRepository.save(centro);
    }

    public void eliminar(Long id) {
        centroRepository.deleteById(id);
    }
}

