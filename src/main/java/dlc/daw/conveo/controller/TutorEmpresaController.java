package dlc.daw.conveo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dlc.daw.conveo.service.EstudianteService;
import dlc.daw.conveo.service.TutorEmpresaService;

@Controller
public class TutorEmpresaController {

    private final TutorEmpresaService tutorEmpresaService;
    private final EstudianteService estudianteService;

    public TutorEmpresaController(TutorEmpresaService tutorEmpresaService, EstudianteService estudianteService) {
        this.tutorEmpresaService = tutorEmpresaService;
        this.estudianteService = estudianteService;
    }

    @GetMapping("/mis-estudiantes")
    public String misEstudiantes(Authentication authentication, Model model) {
        String email = authentication.getName();
        var tutor = tutorEmpresaService.buscarPorEmailUsuario(email);

        if (tutor == null) {
            model.addAttribute("estudiantes", java.util.List.of());
            model.addAttribute("diasRestantesMap", java.util.Map.of());
            return "tutor/mis-estudiantes";
        }

        var estudiantes = estudianteService.listarPorTutorEmpresa(tutor.getId());

        java.util.Map<Long, Long> diasRestantesMap = new java.util.HashMap<>();
        for (var e : estudiantes) {
            if (e.getFechaFinPracticas() != null) {
                long dias = java.time.temporal.ChronoUnit.DAYS.between(
                        java.time.LocalDate.now(), e.getFechaFinPracticas());
                diasRestantesMap.put(e.getId(), dias);
            }
        }

        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("diasRestantesMap", diasRestantesMap);
        return "tutor/mis-estudiantes";
    }

    @GetMapping("/mi-perfil")
    public String miPerfil(Authentication authentication, Model model) {
        String email = authentication.getName();
        var tutor = tutorEmpresaService.buscarPorEmailUsuario(email);
        model.addAttribute("tutor", tutor);
        return "tutor/mi-perfil";
    }
}
