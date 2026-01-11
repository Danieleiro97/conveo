package dlc.daw.conveo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dlc.daw.conveo.model.Estudiante;
import dlc.daw.conveo.model.TutorEmpresa;
import dlc.daw.conveo.service.AsignacionTutorEmpresaService;
import dlc.daw.conveo.service.CentroService;
import dlc.daw.conveo.service.ConvenioService;
import dlc.daw.conveo.service.EstudianteService;
import dlc.daw.conveo.service.TitulacionService;
import dlc.daw.conveo.service.TutorEmpresaService;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final ConvenioService convenioService;
    private final CentroService centroService;
    private final TitulacionService titulacionService;
    private final TutorEmpresaService tutorEmpresaService;
    private final AsignacionTutorEmpresaService asignacionTutorEmpresaService;

    public EstudianteController(EstudianteService estudianteService,
            ConvenioService convenioService,
            CentroService centroService,
            TitulacionService titulacionService,
            TutorEmpresaService tutorEmpresaService,
            AsignacionTutorEmpresaService asignacionTutorEmpresaService) {
        this.estudianteService = estudianteService;
        this.convenioService = convenioService;
        this.centroService = centroService;
        this.titulacionService = titulacionService;
        this.tutorEmpresaService = tutorEmpresaService;
        this.asignacionTutorEmpresaService = asignacionTutorEmpresaService;
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
        model.addAttribute("tutoresEmpresa", tutorEmpresaService.listarTodos());

        return "estudiantes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Estudiante estudiante,
            @RequestParam Long centroId,
            @RequestParam Long titulacionId,
            @RequestParam(required = false) Long convenioId,
            @RequestParam(required = false) Long tutorEmpresaId) {

        estudiante.setCentro(centroService.buscarPorId(centroId));
        estudiante.setTitulacion(titulacionService.buscarPorId(titulacionId));

        if (convenioId != null)
            estudiante.setConvenio(convenioService.buscarPorId(convenioId));
        else
            estudiante.setConvenio(null);

        TutorEmpresa nuevoTutor = (tutorEmpresaId != null)
                ? tutorEmpresaService.buscarPorId(tutorEmpresaId)
                : null;

        // 1) Guardar primero (asegura ID si es nuevo)
        estudianteService.guardar(estudiante);

        // 2) Registrar histórico (usa estudiante.getId())
        asignacionTutorEmpresaService.actualizarAsignacionTutor(estudiante, nuevoTutor);

        // 3) Setear tutor actual y guardar
        estudiante.setTutorEmpresa(nuevoTutor);
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
        model.addAttribute("tutoresEmpresa", tutorEmpresaService.listarTodos());
        return "estudiantes/formulario";
    }
}