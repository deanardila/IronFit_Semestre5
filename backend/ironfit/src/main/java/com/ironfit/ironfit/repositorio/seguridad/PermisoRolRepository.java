package com.ironfit.ironfit.repositorio.seguridad;

import com.ironfit.ironfit.modelo.seguridad.PermisoRol;
import com.ironfit.ironfit.modelo.seguridad.PermisoRolId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermisoRolRepository extends JpaRepository<PermisoRol, PermisoRolId> {

    List<PermisoRol> findByRol_IdRol(Long idRol);
}
