package dlc.daw.conveo.controller;

import dlc.daw.conveo.service.AsignacionTutorEmpresaService;
import dlc.daw.conveo.service.EstudianteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estudiantes")
public class HistorialTutorController {

    private final EstudianteService estudianteService;
    private final AsignacionTutorEmpresaService asignacionTutorEmpresaService;

    public HistorialTutorController(EstudianteService estudianteService,
                                    AsignacionTutorEmpresaService asignacionTutorEmpresaService) {
        this.estudianteService = estudianteService;
        this.asignacionTutorEmpresaService = asignacionTutorEmpresaService;
    }

    @GetMapping("/{id}/historial-tutor")
    public String historialTutor(@PathVariable Long id, Model model) {
        var estudiante = estudianteService.buscarPorId(id);
        var historial = asignacionTutorEmpresaService.historialPorEstudiante(id);

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("historial", historial);

        return "estudiantes/historial-tutor";
    }
}
