package dlc.daw.conveo.controller;

import dlc.daw.conveo.model.Rol;
import dlc.daw.conveo.model.TutorEmpresa;
import dlc.daw.conveo.model.Usuario;
import dlc.daw.conveo.service.TutorEmpresaService;
import dlc.daw.conveo.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                          @RequestParam String password) {

        // Crear Usuario (email del tutor)
        Usuario usuario = usuarioService.crearSiNoExiste(tutor.getEmail(), password, Rol.TUTOR_EMPRESA);

        tutor.setUsuario(usuario);
        tutorEmpresaService.guardar(tutor);

        return "redirect:/tutores-empresa";
    }
}
