package dlc.daw.conveo.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dlc.daw.conveo.exception.ReglaNegocioException;
import dlc.daw.conveo.model.Estudiante;
import dlc.daw.conveo.model.TutorEmpresa;
import dlc.daw.conveo.service.AsignacionTutorEmpresaService;
import dlc.daw.conveo.service.CentroService;
import dlc.daw.conveo.service.ConvenioService;
import dlc.daw.conveo.service.EmailService;
import dlc.daw.conveo.service.EstudianteService;
import dlc.daw.conveo.service.SeguimientoTutorService;
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
    private final EmailService emailService;
    private final SeguimientoTutorService seguimientoTutorService;

    public EstudianteController(EstudianteService estudianteService,
            ConvenioService convenioService,
            CentroService centroService,
            TitulacionService titulacionService,
            TutorEmpresaService tutorEmpresaService,
            AsignacionTutorEmpresaService asignacionTutorEmpresaService,
            EmailService emailService,
            SeguimientoTutorService seguimientoTutorService) {
        this.estudianteService = estudianteService;
        this.convenioService = convenioService;
        this.centroService = centroService;
        this.titulacionService = titulacionService;
        this.tutorEmpresaService = tutorEmpresaService;
        this.asignacionTutorEmpresaService = asignacionTutorEmpresaService;
        this.emailService = emailService;
        this.seguimientoTutorService = seguimientoTutorService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) Long centroId,
            @RequestParam(required = false) Long titulacionId,
            @RequestParam(required = false) Long convenioId,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) Boolean tutorAsignado,
            Model model) {

        List<Estudiante> estudiantes = estudianteService.buscarConFiltros(centroId, titulacionId, convenioId, activo,
                tutorAsignado);
        model.addAttribute("estudiantes", estudiantes);
        // para pintar selects y mantener selección
        model.addAttribute("centros", centroService.listarTodos());
        model.addAttribute("titulaciones", titulacionService.listarTodas());
        model.addAttribute("convenios", convenioService.listarTodos());

        model.addAttribute("centroId", centroId);
        model.addAttribute("titulacionId", titulacionId);
        model.addAttribute("convenioId", convenioId);
        model.addAttribute("activo", activo);
        model.addAttribute("tutorAsignado", tutorAsignado);

        Map<Long, Long> diasRestantesMap = new HashMap<>();
        for (Estudiante e : estudiantes) {
            if (e.getFechaFinPracticas() != null) {
                long dias = ChronoUnit.DAYS.between(LocalDate.now(), e.getFechaFinPracticas());
                diasRestantesMap.put(e.getId(), dias);
            }
        }
        model.addAttribute("diasRestantesMap", diasRestantesMap);

        return "estudiantes/lista";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        var estudiante = estudianteService.buscarPorId(id);
        model.addAttribute("estudiante", estudiante);

        long diasRestantes = Long.MAX_VALUE;
        if (estudiante.getFechaFinPracticas() != null) {
            diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), estudiante.getFechaFinPracticas());
        }
        model.addAttribute("diasRestantes", diasRestantes);
        model.addAttribute("seguimientos", seguimientoTutorService.listarPorEstudiante(id));

        return "estudiantes/detalle";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        cargarListasFormulario(model);
        return "estudiantes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Estudiante estudiante,
            @RequestParam Long centroId,
            @RequestParam Long titulacionId,
            @RequestParam(required = false) Long convenioId,
            @RequestParam(required = false) Long tutorEmpresaId,
            Model model) {

        estudiante.setCentro(centroService.buscarPorId(centroId));
        estudiante.setTitulacion(titulacionService.buscarPorId(titulacionId));

        if (convenioId != null) {
            estudiante.setConvenio(convenioService.buscarPorId(convenioId));
        } else {
            estudiante.setConvenio(null);
        }

        TutorEmpresa nuevoTutor = (tutorEmpresaId != null)
                ? tutorEmpresaService.buscarPorId(tutorEmpresaId)
                : null;

        try {
            // 1) Guardar primero (asegura ID si es nuevo) + VALIDACIONES
            estudianteService.guardarValidando(estudiante);

            // 2) Registrar histórico (usa estudiante.getId())
            asignacionTutorEmpresaService.actualizarAsignacionTutor(estudiante, nuevoTutor);

            // 3) Setear tutor actual y guardar (con validación también)
            estudiante.setTutorEmpresa(nuevoTutor);
            estudianteService.guardarValidando(estudiante);

            return "redirect:/estudiantes";

        } catch (ReglaNegocioException ex) {
            // Volver al formulario con mensaje y listas cargadas
            model.addAttribute("errorRegla", ex.getMessage());
            model.addAttribute("estudiante", estudiante);
            cargarListasFormulario(model);
            return "estudiantes/formulario";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String darDeBaja(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.buscarPorId(id);

        // Cerrar asignación activa con el tutor
        asignacionTutorEmpresaService.actualizarAsignacionTutor(estudiante, null);

        // Tu lógica de baja que ya tenías:
        estudianteService.eliminar(id);

        return "redirect:/estudiantes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("estudiante", estudianteService.buscarPorId(id));
        cargarListasFormulario(model);
        return "estudiantes/formulario";
    }

    private void cargarListasFormulario(Model model) {
        model.addAttribute("centros", centroService.listarTodos());
        model.addAttribute("convenios", convenioService.listarTodos());
        model.addAttribute("titulaciones", titulacionService.listarTodas());
        model.addAttribute("tutoresEmpresa", tutorEmpresaService.listarTodos());
    }

    @PostMapping("/{id}/recordatorio")
    public String enviarRecordatorioManual(@PathVariable Long id,
            @RequestParam(defaultValue = "detalle") String origen,
            RedirectAttributes ra) {
        var estudiante = estudianteService.buscarPorId(id);

        long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), estudiante.getFechaFinPracticas());

        if (diasRestantes <= 15 && diasRestantes >= 0
                && estudiante.getTutorEmpresa() != null
                && estudiante.getTutorEmpresa().getEmail() != null) {

            emailService.enviarRecordatorioFinPracticas(
                    estudiante.getTutorEmpresa().getEmail(),
                    estudiante.getNombre() + " " + estudiante.getApellidos(),
                    String.valueOf(estudiante.getFechaFinPracticas()),
                    diasRestantes);
            ra.addFlashAttribute("mensajeExito", "Recordatorio enviado al tutor de empresa.");
        }

        if (origen.equals("lista")) {
            return "redirect:/estudiantes";
        }
        return "redirect:/estudiantes/" + id;
    }

}
