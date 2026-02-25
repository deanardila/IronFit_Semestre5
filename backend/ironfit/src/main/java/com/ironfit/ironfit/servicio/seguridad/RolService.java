package com.ironfit.ironfit.servicio.seguridad;

import com.ironfit.ironfit.modelo.seguridad.Rol;
import com.ironfit.ironfit.repositorio.seguridad.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    public Optional<Rol> buscarPorId(Long idRol) {
        return rolRepository.findById(idRol);
    }

    public Optional<Rol> buscarPorNombre(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol);
    }

    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }

    public void eliminar(Long idRol) {
        rolRepository.deleteById(idRol);
    }
}

