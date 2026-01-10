package dlc.daw.conveo.service;

import dlc.daw.conveo.model.*;
import dlc.daw.conveo.repository.CentroTitulacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CentroTitulacionService {

    private final CentroTitulacionRepository repo;

    public CentroTitulacionService(CentroTitulacionRepository repo) {
        this.repo = repo;
    }

    public List<CentroTitulacion> listarPorCentro(Long centroId) {
        return repo.findByCentro_Id(centroId);
    }

    @Transactional
    public void reemplazarTitulacionesDeCentro(Centro centro, List<Titulacion> titulaciones) {
        repo.deleteByCentro_Id(centro.getId());

        for (Titulacion t : titulaciones) {
            CentroTitulacion ct = new CentroTitulacion();
            ct.setCentro(centro);
            ct.setTitulacion(t);
            ct.setId(new CentroTitulacionId(centro.getId(), t.getId()));
            repo.save(ct);
        }
    }
}
