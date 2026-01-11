package dlc.daw.conveo.config;

import dlc.daw.conveo.model.Rol;
import dlc.daw.conveo.model.Usuario;
import dlc.daw.conveo.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByEmail("admin@conveo.local").isEmpty()) {
            var admin = new Usuario(null, "admin@conveo.local",
                    passwordEncoder.encode("admin1234"),
                    Rol.ADMIN, true);
            usuarioRepository.save(admin);
        }

        if (usuarioRepository.findByEmail("rrhh@conveo.local").isEmpty()) {
            var rrhh = new Usuario(null, "rrhh@conveo.local",
                    passwordEncoder.encode("rrhh1234"),
                    Rol.RRHH, true);
            usuarioRepository.save(rrhh);
        }
    }
}
