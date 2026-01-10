package dlc.daw.conveo.controller;

import dlc.daw.conveo.model.Estudiante;
import dlc.daw.conveo.service.ConvenioService;
import dlc.daw.conveo.service.EstudianteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final ConvenioService convenioService;

    public EstudianteController(EstudianteService estudianteService,
            ConvenioService convenioService) {
        this.estudianteService = estudianteService;
        this.convenioService = convenioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.listarTodos());
        return "estudiantes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("convenios", convenioService.listarTodos());
        return "estudiantes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Estudiante estudiante,
            @RequestParam Long convenioId) {

        if (convenioId != null) {
            estudiante.setConvenio(convenioService.buscarPorId(convenioId));
        }

        estudianteService.guardar(estudiante);
        return "redirect:/estudiantes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return "redirect:/estudiantes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("estudiante", estudianteService.buscarPorId(id));
        model.addAttribute("convenios", convenioService.listarTodos());
        return "estudiantes/formulario";
    }
}