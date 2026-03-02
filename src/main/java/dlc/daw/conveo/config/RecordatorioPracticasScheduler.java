package dlc.daw.conveo.config;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dlc.daw.conveo.model.Estudiante;
import dlc.daw.conveo.repository.EstudianteRepository;
import dlc.daw.conveo.service.EmailService;

@Component
public class RecordatorioPracticasScheduler {

    private final EstudianteRepository estudianteRepository;
    private final EmailService emailService;

    public RecordatorioPracticasScheduler(EstudianteRepository estudianteRepository,
                                          EmailService emailService) {
        this.estudianteRepository = estudianteRepository;
        this.emailService = emailService;
    }

    // Se ejecuta todos los días a las 08:00
    @Scheduled(cron = "0 0 8 * * *")
    public void enviarRecordatorios() {
        // Busca exactamente los que faltan 15 días
        LocalDate objetivo = LocalDate.now().plusDays(15);

        List<Estudiante> estudiantes = estudianteRepository
                .findByActivoTrueAndTutorEmpresaIsNotNullAndFechaFinPracticas(objetivo);

        for (Estudiante e : estudiantes) {
            if (e.getTutorEmpresa().getEmail() == null) continue;

            long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), e.getFechaFinPracticas());
            String nombreEstudiante = e.getNombre() + " " + e.getApellidos();
            String destinatario = e.getTutorEmpresa().getEmail();

            emailService.enviarRecordatorioFinPracticas(
                    destinatario,
                    nombreEstudiante,
                    String.valueOf(e.getFechaFinPracticas()),
                    diasRestantes
            );
        }
    }
}