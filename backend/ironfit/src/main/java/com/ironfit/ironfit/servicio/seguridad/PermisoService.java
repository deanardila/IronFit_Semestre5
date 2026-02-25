package com.ironfit.ironfit.servicio.seguridad;

import com.ironfit.ironfit.modelo.seguridad.*;
import com.ironfit.ironfit.repositorio.seguridad.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoService {

    private final PermisoRepository permisoRepository;
    private final RolRepository rolRepository;
    private final PermisoRolRepository permisoRolRepository;

    public PermisoService(PermisoRepository permisoRepository,
                            RolRepository rolRepository,
                            PermisoRolRepository permisoRolRepository) {
        this.permisoRepository = permisoRepository;
        this.rolRepository = rolRepository;
        this.permisoRolRepository = permisoRolRepository;
    }

    // -------------------- CRUD BÁSICO PERMISO --------------------

    public List<Permiso> listarTodos() {
        return permisoRepository.findAll();
    }

    public Optional<Permiso> buscarPorId(Long idPermiso) {
        return permisoRepository.findById(idPermiso);
    }

    public Permiso guardar(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    public void eliminar(Long idPermiso) {
        permisoRepository.deleteById(idPermiso);
    }

    // -------------------- PERMISOS DE ROLES --------------------

    @Transactional
    public void asignarPermisoARol(Long idRol, Long idPermiso) {

        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + idRol));

        Permiso permiso = permisoRepository.findById(idPermiso)
                .orElseThrow(() -> new IllegalArgumentException("Permiso no encontrado: " + idPermiso));

        PermisoRolId id = new PermisoRolId();
        id.setIdRol(idRol);
        id.setIdPermiso(idPermiso);

        if (permisoRolRepository.existsById(id)) {
            return; // ya está asignado
        }

        PermisoRol pr = new PermisoRol();
        pr.setId(id);
        pr.setRol(rol);
        pr.setPermiso(permiso);

        permisoRolRepository.save(pr);
    }

    @Transactional
    public void quitarPermisoARol(Long idRol, Long idPermiso) {
        PermisoRolId id = new PermisoRolId();
        id.setIdRol(idRol);
        id.setIdPermiso(idPermiso);

        if (permisoRolRepository.existsById(id)) {
            permisoRolRepository.deleteById(id);
        }
    }

    public List<PermisoRol> listarPermisosDeRol(Long idRol) {
        return permisoRolRepository.findByRol_IdRol(idRol);
    }
}
