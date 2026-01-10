package dlc.daw.conveo.controller;

import dlc.daw.conveo.model.Convenio;
import dlc.daw.conveo.service.ConvenioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/convenios")
public class ConvenioController {

    private final ConvenioService convenioService;

    public ConvenioController(ConvenioService convenioService) {
        this.convenioService = convenioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("convenios", convenioService.listarTodos());
        return "convenios/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("convenio", new Convenio());
        return "convenios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Convenio convenio) {
        convenioService.guardar(convenio);
        return "redirect:/convenios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        convenioService.eliminar(id);
        return "redirect:/convenios";
    }

    @GetMapping("/editar/{id}")
public String editar(@PathVariable Long id, Model model) {
    Convenio convenio = convenioService.buscarPorId(id);
    model.addAttribute("convenio", convenio);
    return "convenios/formulario";
}
}