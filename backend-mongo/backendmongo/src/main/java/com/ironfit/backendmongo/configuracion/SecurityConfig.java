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

                //Permisos de acceso 
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/test/**").permitAll()

                // usuarios
                .requestMatchers("/api/usuarios/me").authenticated()
                .requestMatchers("/api/usuarios/clientes/buscar").hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                // ejercicios
                .requestMatchers(HttpMethod.GET, "/api/ejercicios/**").hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers(HttpMethod.POST, "/api/ejercicios/**").hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers(HttpMethod.PUT, "/api/ejercicios/**").hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers(HttpMethod.PATCH, "/api/ejercicios/**").hasAnyRole("ADMIN", "ENTRENADOR")

                // planes
                .requestMatchers(HttpMethod.GET, "/api/planes/**").hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers(HttpMethod.POST, "/api/planes/**").hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers(HttpMethod.PUT, "/api/planes/**").hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers(HttpMethod.PATCH, "/api/planes/**").hasAnyRole("ADMIN", "ENTRENADOR")


                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}