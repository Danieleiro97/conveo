package dlc.daw.conveo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Authentication auth) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TUTOR_EMPRESA"))) {
            return "redirect:/mis-estudiantes";
        }
        return "redirect:/estudiantes";
    }
}
