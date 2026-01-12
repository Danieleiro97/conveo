package dlc.daw.conveo.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RoleBasedSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isRRHH = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_RRHH"));
        boolean isTutor = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_TUTOR_EMPRESA"));

        if (isTutor) {
            response.sendRedirect(request.getContextPath() + "/mis-estudiantes");
            return;
        }

        if (isAdmin || isRRHH) {
            response.sendRedirect(request.getContextPath() + "/estudiantes");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/");
    }
}
