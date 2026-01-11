package dlc.daw.conveo.controller;

import dlc.daw.conveo.service.AsignacionTutorEmpresaService;
import dlc.daw.conveo.service.TutorEmpresaService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TutorEmpresaHistorialController {

    private final TutorEmpresaService tutorEmpresaService;
    private final AsignacionTutorEmpresaService asignacionTutorEmpresaService;

    public TutorEmpresaHistorialController(TutorEmpresaService tutorEmpresaService,
            AsignacionTutorEmpresaService asignacionTutorEmpresaService) {
        this.tutorEmpresaService = tutorEmpresaService;
        this.asignacionTutorEmpresaService = asignacionTutorEmpresaService;
    }

    @GetMapping("/mi-historial-estudiantes")
    public String miHistorial(Authentication authentication, Model model) {
        String email = authentication.getName();
        var tutor = tutorEmpresaService.buscarPorEmailUsuario(email);

        if (tutor == null) {
            model.addAttribute("historial", java.util.List.of());
            return "tutor/historial-estudiantes";
        }

        model.addAttribute("historial", asignacionTutorEmpresaService.historialPorTutor(tutor.getId()));
        return "tutor/historial-estudiantes";
    }
}
