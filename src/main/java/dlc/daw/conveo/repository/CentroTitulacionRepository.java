package dlc.daw.conveo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import dlc.daw.conveo.model.CentroTitulacion;
import dlc.daw.conveo.model.CentroTitulacionId;

public interface CentroTitulacionRepository extends JpaRepository<CentroTitulacion, CentroTitulacionId> {

    List<CentroTitulacion> findByCentro_Id(Long centroId);

    @Modifying
    @Transactional
    void deleteByCentro_Id(Long centroId);
}
