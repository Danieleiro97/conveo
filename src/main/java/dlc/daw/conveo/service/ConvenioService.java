package dlc.daw.conveo.service;

import dlc.daw.conveo.exception.ReglaNegocioException;
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

    public void guardarValidando(Convenio convenio) {

        // Regla: si se marca como activo, no puede existir otro convenio activo en el
        // mismo centro
        if (convenio.isActivo() && convenio.getCentro() != null && convenio.getCentro().getId() != null) {

            Long centroId = convenio.getCentro().getId();

            boolean existeOtroActivo;

            if (convenio.getId() == null) {
                // Caso: nuevo convenio
                existeOtroActivo = convenioRepository
                        .findFirstByCentro_IdAndActivoTrue(centroId)
                        .isPresent();
            } else {
                // Caso: edición convenio existente (excluyendo el propio)
                existeOtroActivo = convenioRepository
                        .findFirstByCentro_IdAndActivoTrueAndIdNot(centroId, convenio.getId())
                        .isPresent();
            }

            if (existeOtroActivo) {
                throw new ReglaNegocioException(
                        "No se puede activar el convenio: el centro ya tiene otro convenio activo.");
            }
        }

        convenioRepository.save(convenio);
    }

    public void eliminar(Long id) {
        convenioRepository.deleteById(id);
    }

    public Convenio buscarPorId(Long id) {
        return convenioRepository.findById(id).orElse(null);
    }
}