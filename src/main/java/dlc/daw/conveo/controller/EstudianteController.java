package dlc.daw.conveo.controller;

import dlc.daw.conveo.model.Estudiante;
import dlc.daw.conveo.service.ConvenioService;
import dlc.daw.conveo.service.EstudianteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import dlc.daw.conveo.service.CentroService;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final ConvenioService convenioService;
    private final CentroService centroService;

    public EstudianteController(EstudianteService estudianteService,
            ConvenioService convenioService,
            CentroService centroService) {
        this.estudianteService = estudianteService;
        this.convenioService = convenioService;
        this.centroService = centroService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.listarTodos());
        return "estudiantes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("centros", centroService.listarTodos());
        model.addAttribute("convenios", convenioService.listarTodos());
        return "estudiantes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Estudiante estudiante,
            @RequestParam Long centroId,
            @RequestParam(required = false) Long convenioId) {

        estudiante.setCentro(centroService.buscarPorId(centroId));

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
        model.addAttribute("centros", centroService.listarTodos());
        return "estudiantes/formulario";
    }
}