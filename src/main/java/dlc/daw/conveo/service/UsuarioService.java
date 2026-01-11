package dlc.daw.conveo.service;

import dlc.daw.conveo.model.Rol;
import dlc.daw.conveo.model.Usuario;
import dlc.daw.conveo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario crearSiNoExiste(String email, String rawPassword, Rol rol) {
        return repo.findByEmail(email).orElseGet(() -> {
            Usuario u = new Usuario();
            u.setEmail(email);
            u.setPassword(passwordEncoder.encode(rawPassword));
            u.setRol(rol);
            u.setActivo(true);
            return repo.save(u);
        });
    }
}
