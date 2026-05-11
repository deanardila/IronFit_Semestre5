package com.ironfit.backendmongo.configuracion;

import com.ironfit.backendmongo.seguridad.jwt.JwtAuthFilter;
import com.ironfit.backendmongo.seguridad.jwt.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));               // acepta cualquier origen
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));                      // acepta cualquier header
        config.setExposedHeaders(List.of("Authorization"));          // exponer el token al frontend
        config.setAllowCredentials(false);                           // obligatorio con patrones '*'
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtService jwtService,
            CorsConfigurationSource corsConfigurationSource
    ) throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.disable())
            .sessionManagement(sm ->
                sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/test/**").permitAll()
                .requestMatchers("/api/usuarios/me").authenticated()
                .requestMatchers("/api/usuarios/clientes/buscar")
                    .hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/ejercicios/**")
                    .hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers(HttpMethod.POST, "/api/ejercicios/**")
                    .hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers(HttpMethod.PUT, "/api/ejercicios/**")
                    .hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers(HttpMethod.PATCH, "/api/ejercicios/**")
                    .hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers(HttpMethod.GET, "/api/planes/**")
                    .hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers(HttpMethod.POST, "/api/planes/**")
                    .hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers(HttpMethod.PUT, "/api/planes/**")
                    .hasAnyRole("ADMIN", "ENTRENADOR")
                .requestMatchers(HttpMethod.PATCH, "/api/planes/**")
                    .hasAnyRole("ADMIN", "ENTRENADOR")
                .anyRequest().authenticated()
            )
            .addFilterBefore(
                new JwtAuthFilter(jwtService),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}