package dlc.daw.conveo.controller;

import dlc.daw.conveo.model.Rol;
import dlc.daw.conveo.model.TutorEmpresa;
import dlc.daw.conveo.model.Usuario;
import dlc.daw.conveo.service.TutorEmpresaService;
import dlc.daw.conveo.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tutores-empresa")
public class AdminTutorEmpresaController {

    private final TutorEmpresaService tutorEmpresaService;
    private final UsuarioService usuarioService;

    public AdminTutorEmpresaController(TutorEmpresaService tutorEmpresaService, UsuarioService usuarioService) {
        this.tutorEmpresaService = tutorEmpresaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tutores", tutorEmpresaService.listarTodos());
        return "tutores-empresa/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("tutor", new TutorEmpresa());
        return "tutores-empresa/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute TutorEmpresa tutor,
            @RequestParam(required = false) String password) {

        // Si es NUEVO: crear usuario con password
        if (tutor.getId() == null) {
            if (password == null || password.isBlank()) {
                // Si quieres, aquí podrías devolver error, pero lo dejamos simple:
                return "redirect:/tutores-empresa";
            }

            Usuario usuario = usuarioService.crearSiNoExiste(tutor.getEmail(), password, Rol.TUTOR_EMPRESA);
            tutor.setUsuario(usuario);
        } else {
            // Si es EDICIÓN: no recreamos usuario, lo recuperamos de BBDD para mantener
            // relación
            TutorEmpresa existente = tutorEmpresaService.buscarPorId(tutor.getId());
            tutor.setUsuario(existente.getUsuario());
        }

        tutorEmpresaService.guardar(tutor);
        return "redirect:/tutores-empresa";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("tutor", tutorEmpresaService.buscarPorId(id));
        return "tutores-empresa/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {

        long activos = tutorEmpresaService.eliminarTutorEmpresaDesasignandoEstudiantes(id);

        if (activos > 0) {
            ra.addFlashAttribute("mensaje",
                    "El tutor se ha eliminado. Tenía " + activos
                            + " estudiante(s) en activo, que han pasado a no tener tutor asignado.");
        } else {
            ra.addFlashAttribute("mensaje",
                    "El tutor se ha eliminado. Los estudiantes asignados (si existían) han pasado a no tener tutor asignado.");
        }

        return "redirect:/tutores-empresa";
    }

}
