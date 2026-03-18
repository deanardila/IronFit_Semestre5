package com.ironfit.backendmongo.configuracion;

import com.ironfit.backendmongo.seguridad.jwt.JwtAuthFilter;
import com.ironfit.backendmongo.seguridad.jwt.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtService jwtService) throws Exception {
        http
            .cors(Customizer.withDefaults()) // <-- esto activa CORS en Spring Security
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/test/**").permitAll()
                .requestMatchers("/api/usuarios/me").authenticated()
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/ejercicios/**").hasAnyRole("ADMIN", "ENTRENADOR", "CLIENTE")
                .requestMatchers(HttpMethod.POST, "/api/ejercicios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/ejercicios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/ejercicios/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}