package dlc.daw.conveo.controller;

import dlc.daw.conveo.model.Convenio;
import dlc.daw.conveo.service.ConvenioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import dlc.daw.conveo.service.CentroService;

@Controller
@RequestMapping("/convenios")
public class ConvenioController {

    private final ConvenioService convenioService;

    private final CentroService centroService;

    public ConvenioController(ConvenioService convenioService,
            CentroService centroService) {
        this.convenioService = convenioService;
        this.centroService = centroService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("convenios", convenioService.listarTodos());
        return "convenios/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("convenio", new Convenio());
        model.addAttribute("centros", centroService.listarTodos());
        return "convenios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Convenio convenio,
            @RequestParam Long centroId) {

        convenio.setCentro(centroService.buscarPorId(centroId));
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