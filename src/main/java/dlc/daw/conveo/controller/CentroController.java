package dlc.daw.conveo.controller;

import dlc.daw.conveo.model.Centro;
import dlc.daw.conveo.service.CentroService;
import dlc.daw.conveo.service.CentroTitulacionService;
import dlc.daw.conveo.service.TitulacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/centros")
public class CentroController {

    private final CentroService centroService;
    private final TitulacionService titulacionService;
    private final CentroTitulacionService centroTitulacionService;

    public CentroController(CentroService centroService,
            TitulacionService titulacionService,
            CentroTitulacionService centroTitulacionService) {
        this.centroService = centroService;
        this.titulacionService = titulacionService;
        this.centroTitulacionService = centroTitulacionService;
    }

    @GetMapping
    public String listar(Model model) {
        var centros = centroService.listarTodos();
        model.addAttribute("centros", centros);

        // Mapa: centroId -> "DAW, DAM, ASIR"
        var titulosPorCentro = new java.util.HashMap<Long, String>();

        for (var c : centros) {
            var relaciones = centroTitulacionService.listarPorCentro(c.getId());

            var nombres = relaciones.stream()
                    .map(ct -> ct.getTitulacion().getNombre())
                    .distinct()
                    .sorted()
                    .toList();

            titulosPorCentro.put(c.getId(), String.join(", ", nombres));
        }

        model.addAttribute("titulacionesPorCentro", titulosPorCentro);
        return "centros/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("centro", new Centro());
        model.addAttribute("titulaciones", titulacionService.listarTodas());
        model.addAttribute("asignadas", java.util.List.of());
        return "centros/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Centro centro,
            @RequestParam(required = false, name = "titulacionIds") java.util.List<Long> titulacionIds) {

        centroService.guardar(centro);

        var seleccionadas = (titulacionIds == null)
                ? java.util.List.<dlc.daw.conveo.model.Titulacion>of()
                : titulacionIds.stream()
                        .map(titulacionService::buscarPorId)
                        .toList();

        centroTitulacionService.reemplazarTitulacionesDeCentro(centro, seleccionadas);

        return "redirect:/centros";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        centroService.eliminar(id);
        return "redirect:/centros";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var centro = centroService.buscarPorId(id);

        var asignadas = centroTitulacionService.listarPorCentro(id)
                .stream()
                .map(ct -> ct.getTitulacion().getId())
                .toList();

        model.addAttribute("centro", centro);
        model.addAttribute("titulaciones", titulacionService.listarTodas());
        model.addAttribute("asignadas", asignadas);

        return "centros/formulario";
    }

}
