package com.ironfit.ironfit.repositorio.seguridad;

import com.ironfit.ironfit.modelo.seguridad.UsuarioEspecialidad;
import com.ironfit.ironfit.modelo.seguridad.UsuarioEspecialidadId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioEspecialidadRepository
        extends JpaRepository<UsuarioEspecialidad, UsuarioEspecialidadId> {

    List<UsuarioEspecialidad> findByUsuario_IdUsuario(Long idUsuario);
}

