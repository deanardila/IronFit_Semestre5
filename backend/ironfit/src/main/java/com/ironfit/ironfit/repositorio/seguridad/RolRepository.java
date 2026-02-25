package com.ironfit.ironfit.repositorio.seguridad;

import com.ironfit.ironfit.modelo.seguridad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombreRol(String nombreRol);
}
