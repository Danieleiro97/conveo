package dlc.daw.conveo.controller;

import dlc.daw.conveo.model.Convenio;
import dlc.daw.conveo.service.ConvenioService;
import dlc.daw.conveo.service.TitulacionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import dlc.daw.conveo.service.CentroService;

@Controller
@RequestMapping("/convenios")
public class ConvenioController {

    private final ConvenioService convenioService;

    private final CentroService centroService;

    private final TitulacionService titulacionService;

    public ConvenioController(ConvenioService convenioService,
            CentroService centroService, TitulacionService titulacionService) {
        this.convenioService = convenioService;
        this.centroService = centroService;
        this.titulacionService = titulacionService;
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
        model.addAttribute("titulaciones", titulacionService.listarTodas());
        return "convenios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Convenio convenio,
            @RequestParam Long centroId, @RequestParam Long titulacionId) {

        convenio.setCentro(centroService.buscarPorId(centroId));
        convenio.setTitulacion(titulacionService.buscarPorId(titulacionId));
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
        model.addAttribute("convenio", convenioService.buscarPorId(id));
        model.addAttribute("centros", centroService.listarTodos());
        model.addAttribute("titulaciones", titulacionService.listarTodas());
        return "convenios/formulario";
    }
}