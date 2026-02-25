package com.ironfit.ironfit.repositorio.seguridad;

import com.ironfit.ironfit.modelo.seguridad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuarioLogin(String usuarioLogin);

    boolean existsByUsuarioLogin(String usuarioLogin);

    boolean existsByNroDoc(String nroDoc);

    
}
