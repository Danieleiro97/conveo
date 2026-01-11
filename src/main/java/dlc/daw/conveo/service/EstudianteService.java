package dlc.daw.conveo.service;

import dlc.daw.conveo.model.Estudiante;
import dlc.daw.conveo.repository.EstudianteRepository;
import org.springframework.stereotype.Service;
import dlc.daw.conveo.exception.ReglaNegocioException;

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

        public List<Estudiante> listarPorTutorEmpresa(Long tutorEmpresaId) {
        return estudianteRepository.findByTutorEmpresa_Id(tutorEmpresaId);
    }

    public List<Estudiante> listarPorConvenio(Long convenioId) {
        return estudianteRepository.findByConvenio_IdOrderByApellidosAscNombreAsc(convenioId);
    }


    public List<Estudiante> buscarConFiltros(Long centroId, Long titulacionId, Long convenioId, Boolean activo,
            Boolean tutorAsignado) {
        return estudianteRepository.buscarConFiltros(centroId, titulacionId, convenioId, activo, tutorAsignado);
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


    public void guardarValidando(Estudiante e) {

        // Fechas prácticas
        if (e.getFechaInicioPracticas() != null && e.getFechaFinPracticas() != null) {
            if (e.getFechaFinPracticas().isBefore(e.getFechaInicioPracticas())) {
                throw new ReglaNegocioException("La fecha fin de prácticas no puede ser anterior a la fecha inicio.");
            }
        }

        // Si estudiante está activo, debe tener convenio y debe estar activo
        if (e.isActivo()) {
            if (e.getConvenio() == null) {
                throw new ReglaNegocioException("Un estudiante activo debe tener un convenio asignado.");
            }
            if (!e.getConvenio().isActivo()) {
                throw new ReglaNegocioException("No se puede asignar a un estudiante activo un convenio inactivo.");
            }
        }

        estudianteRepository.save(e);
    }

}
