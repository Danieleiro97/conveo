package dlc.daw.conveo.controller;

import dlc.daw.conveo.model.Estudiante;
import dlc.daw.conveo.service.ConvenioService;
import dlc.daw.conveo.service.EstudianteService;
import dlc.daw.conveo.service.TitulacionService;

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
    private final TitulacionService titulacionService;

    public EstudianteController(EstudianteService estudianteService,
            ConvenioService convenioService,
            CentroService centroService,
            TitulacionService titulacionService) {
        this.estudianteService = estudianteService;
        this.convenioService = convenioService;
        this.centroService = centroService;
        this.titulacionService = titulacionService;
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
        model.addAttribute("titulaciones", titulacionService.listarTodas());

        return "estudiantes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Estudiante estudiante,
            @RequestParam Long centroId,
            @RequestParam Long titulacionId,
            @RequestParam(required = false) Long convenioId) {

        estudiante.setCentro(centroService.buscarPorId(centroId));
        estudiante.setTitulacion(titulacionService.buscarPorId(titulacionId));

        if (convenioId != null) {
            estudiante.setConvenio(convenioService.buscarPorId(convenioId));
        } else {
            estudiante.setConvenio(null);
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
        model.addAttribute("titulaciones", titulacionService.listarTodas());
        return "estudiantes/formulario";
    }
}