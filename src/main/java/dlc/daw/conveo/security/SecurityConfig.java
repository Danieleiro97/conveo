package dlc.daw.conveo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final RoleBasedSuccessHandler successHandler;

    public SecurityConfig(RoleBasedSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/webjars/**").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                .requestMatchers("/centros/**").hasRole("ADMIN")
                .requestMatchers("/convenios/**").hasRole("ADMIN")
                .requestMatchers("/tutores-empresa/**").hasAnyRole("ADMIN", "RRHH")
                .requestMatchers("/titulaciones/**").hasRole("ADMIN")

                .requestMatchers("/estudiantes/**").hasAnyRole("ADMIN", "RRHH")

                .requestMatchers("/mis-estudiantes/**").hasRole("TUTOR_EMPRESA")
                .requestMatchers("/mi-historial-estudiantes/**").hasRole("TUTOR_EMPRESA")

                .anyRequest().authenticated()
        );

        http.formLogin(login -> login
                .successHandler(successHandler)   // ✅ aquí
                .permitAll()
        );

        http.logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        return http.build();
    }
}
