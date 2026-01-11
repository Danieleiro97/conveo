package dlc.daw.conveo.controller;

import dlc.daw.conveo.service.AsignacionTutorEmpresaService;
import dlc.daw.conveo.service.TutorEmpresaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tutores-empresa")
public class HistorialTutorEmpresaAdminController {

    private final TutorEmpresaService tutorEmpresaService;
    private final AsignacionTutorEmpresaService asignacionTutorEmpresaService;

    public HistorialTutorEmpresaAdminController(TutorEmpresaService tutorEmpresaService,
                                                AsignacionTutorEmpresaService asignacionTutorEmpresaService) {
        this.tutorEmpresaService = tutorEmpresaService;
        this.asignacionTutorEmpresaService = asignacionTutorEmpresaService;
    }

    @GetMapping("/{id}/historial-estudiantes")
    public String historial(@PathVariable Long id, Model model) {
        var tutor = tutorEmpresaService.buscarPorId(id);
        var historial = asignacionTutorEmpresaService.historialPorTutor(id);

        model.addAttribute("tutor", tutor);
        model.addAttribute("historial", historial);
        return "tutores-empresa/historial-estudiantes";
    }
}
