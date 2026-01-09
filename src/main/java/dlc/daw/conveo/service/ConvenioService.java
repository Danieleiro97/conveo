package dlc.daw.conveo.service;

import dlc.daw.conveo.model.Convenio;
import dlc.daw.conveo.repository.ConvenioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvenioService {

    private final ConvenioRepository convenioRepository;

    public ConvenioService(ConvenioRepository convenioRepository) {
        this.convenioRepository = convenioRepository;
    }

    public List<Convenio> listarTodos() {
        return convenioRepository.findAll();
    }

    public void guardar(Convenio convenio) {
        convenioRepository.save(convenio);
    }

    public void eliminar(Long id) {
        convenioRepository.deleteById(id);
    }

    public Convenio buscarPorId(Long id) {
        return convenioRepository.findById(id).orElse(null);
    }
}