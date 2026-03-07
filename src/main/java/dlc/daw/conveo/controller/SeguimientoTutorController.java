package dlc.daw.conveo.controller;

import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dlc.daw.conveo.model.Estudiante;
import dlc.daw.conveo.model.SeguimientoTutor;
import dlc.daw.conveo.model.TutorEmpresa;
import dlc.daw.conveo.service.EstudianteService;
import dlc.daw.conveo.service.SeguimientoTutorService;
import dlc.daw.conveo.service.TutorEmpresaService;

@Controller
@RequestMapping("/mis-estudiantes")
public class SeguimientoTutorController {

    private final TutorEmpresaService tutorEmpresaService;
    private final EstudianteService estudianteService;
    private final SeguimientoTutorService seguimientoService;

    public SeguimientoTutorController(TutorEmpresaService tutorEmpresaService,
            EstudianteService estudianteService,
            SeguimientoTutorService seguimientoService) {
        this.tutorEmpresaService = tutorEmpresaService;
        this.estudianteService = estudianteService;
        this.seguimientoService = seguimientoService;
    }

    // Ver ficha del estudiante (solo el tutor asignado)
    @GetMapping("/{id}")
    public String fichaEstudiante(@PathVariable Long id,
            Authentication auth, Model model) {
        TutorEmpresa tutor = tutorEmpresaService.buscarPorEmailUsuario(auth.getName());
        Estudiante estudiante = estudianteService.buscarPorId(id);

        if (tutor == null || estudiante == null ||
                !estudiante.getTutorEmpresa().getId().equals(tutor.getId())) {
            return "redirect:/mis-estudiantes";
        }

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("seguimientos",
                seguimientoService.listarPorTutorYEstudiante(tutor.getId(), id));
        return "tutor/ficha-estudiante";
    }

    // Mostrar formulario de seguimiento
    @GetMapping("/{id}/seguimiento/nuevo")
    public String nuevoSeguimiento(@PathVariable Long id,
            Authentication auth, Model model) {
        TutorEmpresa tutor = tutorEmpresaService.buscarPorEmailUsuario(auth.getName());
        Estudiante estudiante = estudianteService.buscarPorId(id);

        if (tutor == null || estudiante == null ||
                !estudiante.getTutorEmpresa().getId().equals(tutor.getId())) {
            return "redirect:/mis-estudiantes";
        }

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("seguimiento", new SeguimientoTutor());
        model.addAttribute("tipos", SeguimientoTutor.TipoSeguimiento.values());
        return "tutor/formulario-seguimiento";
    }

    // Guardar seguimiento
    @PostMapping("/{id}/seguimiento/guardar")
    public String guardarSeguimiento(@PathVariable Long id,
            @RequestParam String tipo,
            @RequestParam Integer valoracion,
            @RequestParam(required = false) String observaciones,
            @RequestParam(required = false) Boolean apto,
            Authentication auth,
            RedirectAttributes ra) {
        TutorEmpresa tutor = tutorEmpresaService.buscarPorEmailUsuario(auth.getName());
        Estudiante estudiante = estudianteService.buscarPorId(id);

        if (tutor == null || estudiante == null ||
                !estudiante.getTutorEmpresa().getId().equals(tutor.getId())) {
            return "redirect:/mis-estudiantes";
        }

        SeguimientoTutor seguimiento = new SeguimientoTutor();
        seguimiento.setEstudiante(estudiante);
        seguimiento.setTutorEmpresa(tutor);
        seguimiento.setFecha(LocalDate.now());
        seguimiento.setTipo(SeguimientoTutor.TipoSeguimiento.valueOf(tipo));
        seguimiento.setValoracion(valoracion);
        seguimiento.setObservaciones(observaciones);
        seguimiento.setApto(apto);

        try {
            seguimientoService.guardar(seguimiento);
            ra.addFlashAttribute("mensajeExito", "Seguimiento registrado correctamente.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("mensajeError", e.getMessage());
        }

        return "redirect:/mis-estudiantes/" + id;
    }
}