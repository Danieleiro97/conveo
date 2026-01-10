package dlc.daw.conveo.controller;

import dlc.daw.conveo.model.Centro;
import dlc.daw.conveo.service.CentroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/centros")
public class CentroController {

    private final CentroService centroService;

    public CentroController(CentroService centroService) {
        this.centroService = centroService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("centros", centroService.listarTodos());
        return "centros/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("centro", new Centro());
        return "centros/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Centro centro) {
        centroService.guardar(centro);
        return "redirect:/centros";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        centroService.eliminar(id);
        return "redirect:/centros";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("centro", centroService.buscarPorId(id));
        return "centros/formulario";
    }
}
