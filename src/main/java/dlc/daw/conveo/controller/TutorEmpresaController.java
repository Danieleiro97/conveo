package dlc.daw.conveo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dlc.daw.conveo.service.EstudianteService;
import dlc.daw.conveo.service.TutorEmpresaService;
import java.util.List;

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
        String email = authentication.getName(); // email del usuario logueado
        var tutor = tutorEmpresaService.buscarPorEmailUsuario(email);

        if (tutor == null) {
            model.addAttribute("estudiantes", java.util.List.of());
            return "tutor/mis-estudiantes";
        }

        model.addAttribute("estudiantes", estudianteService.listarPorTutorEmpresa(tutor.getId()));
        return "tutor/mis-estudiantes";
    }
}
