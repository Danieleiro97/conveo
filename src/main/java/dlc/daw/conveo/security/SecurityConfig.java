package dlc.daw.conveo.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        // estáticos
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // accesoss por roles (ajustable)
                        .requestMatchers("/centros/**").hasRole("ADMIN")
                        .requestMatchers("/convenios/**").hasRole("ADMIN")
                        .requestMatchers("/tutores-empresa/**").hasRole("ADMIN")
                        .requestMatchers("/estudiantes/*/historial-tutor").hasAnyRole("ADMIN", "RRHH")
                        .requestMatchers("/estudiantes/**").hasAnyRole("ADMIN", "RRHH")
                        .requestMatchers("/mi-historial-estudiantes").hasRole("TUTOR_EMPRESA")
                        .requestMatchers("/mis-estudiantes").hasRole("TUTOR_EMPRESA")
                        // resto
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .defaultSuccessUrl("/estudiantes", true)
                        .permitAll())
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}
