package dlc.daw.conveo.repository;

import dlc.daw.conveo.model.TutorEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TutorEmpresaRepository extends JpaRepository<TutorEmpresa, Long> {
    Optional<TutorEmpresa> findByUsuario_Email(String email);
}
