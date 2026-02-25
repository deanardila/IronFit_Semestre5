package com.ironfit.ironfit.repositorio.seguridad;

import com.ironfit.ironfit.modelo.seguridad.UsuarioRol;
import com.ironfit.ironfit.modelo.seguridad.UsuarioRolId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRolId> {

    List<UsuarioRol> findByUsuario_IdUsuario(Long idUsuario);

    List<UsuarioRol> findByRol_IdRol(Long idRol);
    @Query("""
            SELECT COUNT(DISTINCT ur.usuario.idUsuario)
            FROM UsuarioRol ur
            JOIN ur.usuario u
            JOIN ur.rol r
            WHERE UPPER(r.nombreRol) LIKE CONCAT('%', UPPER(:rolNombre), '%')
            AND (u.activo IS NULL OR u.activo = true)
            """)
    long countUsuariosActivosByRol(@Param("rolNombre") String rolNombre);
}
