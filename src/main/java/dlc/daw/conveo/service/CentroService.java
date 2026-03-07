package dlc.daw.conveo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import dlc.daw.conveo.model.Centro;
import dlc.daw.conveo.repository.CentroRepository;
import jakarta.transaction.Transactional;

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

    @Transactional
    public void eliminar(Long id) {
        Centro c = centroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Centro no encontrado"));
        c.setActivo(false);
        c.setFechaBaja(LocalDate.now());
        centroRepository.save(c);
    }

    @Transactional
    public void activar(Long id) {
        Centro c = centroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Centro no encontrado"));
        c.setActivo(true);
        c.setFechaBaja(null);
        centroRepository.save(c);
    }
}
