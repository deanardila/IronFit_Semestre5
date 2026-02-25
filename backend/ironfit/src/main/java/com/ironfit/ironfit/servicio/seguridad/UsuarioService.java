package com.ironfit.ironfit.servicio.seguridad;

import com.ironfit.ironfit.dto.UsuarioCrearDTO;
import com.ironfit.ironfit.dto.UsuarioResumenDTO;
import com.ironfit.ironfit.modelo.seguridad.*;
import com.ironfit.ironfit.repositorio.seguridad.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final EspecialidadRepository especialidadRepository;
    private final UsuarioEspecialidadRepository usuarioEspecialidadRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                            RolRepository rolRepository,
                            UsuarioRolRepository usuarioRolRepository,
                            EspecialidadRepository especialidadRepository,
                            UsuarioEspecialidadRepository usuarioEspecialidadRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.especialidadRepository = especialidadRepository;
        this.usuarioEspecialidadRepository = usuarioEspecialidadRepository;
    }

    // -------------------- CRUD BÁSICO --------------------

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public Optional<Usuario> buscarPorUsuarioLogin(String usuarioLogin) {
        return usuarioRepository.findByUsuarioLogin(usuarioLogin);
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    // -------------------- ROLES --------------------

    @Transactional
    public void asignarRolAUsuario(Long idUsuario, Long idRol) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + idUsuario));

        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + idRol));

        UsuarioRolId id = new UsuarioRolId();
        id.setIdUsuario(usuario.getIdUsuario());
        id.setIdRol(rol.getIdRol());

        // Si ya existe, no hacer nada
        if (usuarioRolRepository.existsById(id)) {
            return;
        }

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setId(id);
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);

        usuarioRolRepository.save(usuarioRol);
    }

    @Transactional
    public void quitarRolDeUsuario(Long idUsuario, Long idRol) {
        UsuarioRolId id = new UsuarioRolId();
        id.setIdUsuario(idUsuario);
        id.setIdRol(idRol);

        if (usuarioRolRepository.existsById(id)) {
            usuarioRolRepository.deleteById(id);
        }
    }

    public List<UsuarioRol> listarRolesDeUsuario(Long idUsuario) {
        return usuarioRolRepository.findByUsuario_IdUsuario(idUsuario);
    }

    // -------------------- ESPECIALIDADES --------------------

    @Transactional
    public void asignarEspecialidadAUsuario(Long idUsuario, Long idEspecialidad) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + idUsuario));

        Especialidad especialidad = especialidadRepository.findById(idEspecialidad)
                .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada: " + idEspecialidad));

        UsuarioEspecialidadId id = new UsuarioEspecialidadId();
        id.setIdUsuario(usuario.getIdUsuario());
        id.setIdEspecialidad(especialidad.getIdEspecialidad());

        if (usuarioEspecialidadRepository.existsById(id)) {
            return;
        }

        UsuarioEspecialidad ue = new UsuarioEspecialidad();
        ue.setId(id);
        ue.setUsuario(usuario);
        ue.setEspecialidad(especialidad);

        usuarioEspecialidadRepository.save(ue);
    }

    @Transactional
    public void quitarEspecialidadDeUsuario(Long idUsuario, Long idEspecialidad) {
        UsuarioEspecialidadId id = new UsuarioEspecialidadId();
        id.setIdUsuario(idUsuario);
        id.setIdEspecialidad(idEspecialidad);

        if (usuarioEspecialidadRepository.existsById(id)) {
            usuarioEspecialidadRepository.deleteById(id);
        }
    }

    public List<UsuarioEspecialidad> listarEspecialidadesDeUsuario(Long idUsuario) {
        return usuarioEspecialidadRepository.findByUsuario_IdUsuario(idUsuario);
    }

    public long contarClientesActivos() {
    return usuarioRolRepository.countUsuariosActivosByRol("CLIENTE");
    }

    public long contarEntrenadoresActivos() {   
    return usuarioRolRepository.countUsuariosActivosByRol("ENTRENADOR");
    }

    public List<UsuarioResumenDTO> obtenerResumenUsuarios() {
    List<Usuario> usuarios = usuarioRepository.findAll();

    return usuarios.stream()
            .map(u -> {
                List<String> roles = u.getUsuarioRoles().stream()
                        .map(UsuarioRol::getRol)
                        .map(r -> r.getNombreRol())   // "ROLE_CLIENTE"
                        .distinct()
                        .toList();

                return new UsuarioResumenDTO(
                        u.getIdUsuario(),
                        u.getTipoDoc(),
                        u.getNroDoc(),
                        u.getNombres(),
                        u.getApellidos(),
                        u.getTelefono(),
                        u.getEmail(),
                        u.getActivo(),
                        roles
                );
            })
            .toList();
}

    @Transactional
    public Usuario crearUsuarioConRol(UsuarioCrearDTO dto) {

        // 1. Crear Usuario
        Usuario u = new Usuario();
        u.setTipoDoc(dto.getTipoDoc());
        u.setNroDoc(dto.getNroDoc());
        u.setNombres(dto.getNombres());
        u.setApellidos(dto.getApellidos());
        u.setTelefono(dto.getTelefono());
        u.setEmail(dto.getEmail());
        u.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        // usuario_login y clave obligatorios
        String login = (dto.getEmail() != null && !dto.getEmail().isBlank())
                ? dto.getEmail()
                : dto.getNroDoc();

        u.setUsuarioLogin(login);
        u.setClave(dto.getNroDoc());  // luego lo puedes encriptar

        Usuario guardado = usuarioRepository.save(u);

        // 2. Buscar rol por nombre (ADMIN / ENTRENADOR / CLIENTE)
        Rol rol = rolRepository.findByNombreRol(dto.getRolNombre())
                .orElseThrow(() -> new RuntimeException("No existe el rol: " + dto.getRolNombre()));

        // 3. Crear registro en usuario_rol
        UsuarioRolId id = new UsuarioRolId();
        id.setIdUsuario(guardado.getIdUsuario());
        id.setIdRol(rol.getIdRol());

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setId(id);
        usuarioRol.setUsuario(guardado);
        usuarioRol.setRol(rol);

        usuarioRolRepository.save(usuarioRol);

        return guardado;
    }
}


