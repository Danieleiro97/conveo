package dlc.daw.conveo.controller;

import dlc.daw.conveo.model.Titulacion;
import dlc.daw.conveo.service.TitulacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/titulaciones")
public class TitulacionController {

    private final TitulacionService titulacionService;

    public TitulacionController(TitulacionService titulacionService) {
        this.titulacionService = titulacionService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulaciones", titulacionService.listarTodas());
        return "titulaciones/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("titulacion", new Titulacion());
        model.addAttribute("niveles", Titulacion.Nivel.values());
        return "titulaciones/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulacion", titulacionService.buscarPorId(id));
        model.addAttribute("niveles", Titulacion.Nivel.values());
        return "titulaciones/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Titulacion titulacion) {
        titulacionService.guardar(titulacion);
        return "redirect:/titulaciones";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        titulacionService.eliminar(id);
        return "redirect:/titulaciones";
    }
}
