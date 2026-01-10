package dlc.daw.conveo.service;

import dlc.daw.conveo.model.Titulacion;
import dlc.daw.conveo.repository.TitulacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitulacionService {

    private final TitulacionRepository titulacionRepository;

    public TitulacionService(TitulacionRepository titulacionRepository) {
        this.titulacionRepository = titulacionRepository;
    }

    public List<Titulacion> listarTodas() {
        return titulacionRepository.findAll();
    }

    public Titulacion buscarPorId(Long id) {
        return titulacionRepository.findById(id).orElse(null);
    }

    public void guardar(Titulacion titulacion) {
        titulacionRepository.save(titulacion);
    }

    public void eliminar(Long id) {
        titulacionRepository.deleteById(id);
    }
}
