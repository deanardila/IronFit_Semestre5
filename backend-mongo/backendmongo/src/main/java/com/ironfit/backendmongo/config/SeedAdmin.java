package com.ironfit.backendmongo.config;

import com.ironfit.backendmongo.usuario.UsuarioDoc;
import com.ironfit.backendmongo.usuario.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class SeedAdmin {

    @Bean
    CommandLineRunner seed(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            String email = "admin@ironfit.com";
            if (!repo.existsByEmail(email)) {
                repo.save(new UsuarioDoc(
                        email,
                        encoder.encode("admin123"),
                        List.of("ADMIN")
                ));
                System.out.println("✅ Admin creado: admin@ironfit.com / admin123");
            }
        };
    }
}