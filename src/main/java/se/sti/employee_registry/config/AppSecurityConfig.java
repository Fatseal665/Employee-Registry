package se.sti.employee_registry.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import se.sti.employee_registry.security.jwt.JwtAuthentiactionFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final JwtAuthentiactionFilter jwtAuthentiactionFilter;
    private final HttpSecurity httpSecurity;

    @Autowired
    public AppSecurityConfig(JwtAuthentiactionFilter jwtAuthentiactionFilter, HttpSecurity httpSecurity) {
        this.jwtAuthentiactionFilter = jwtAuthentiactionFilter;
        this.httpSecurity = httpSecurity;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        httpSecurity
                .csrf(csrfConfigurer -> csrfConfigurer.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/login").permitAll()
                        .requestMatchers("/register","/delete").hasRole("ADMIN")
                        .requestMatchers("/employee-registry").hasAnyRole("EMPLOYEE", "ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(jwtAuthentiactionFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
