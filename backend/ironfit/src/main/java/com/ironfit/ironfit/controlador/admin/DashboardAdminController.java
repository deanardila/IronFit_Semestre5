package com.ironfit.ironfit.controlador.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ironfit.ironfit.servicio.asistencia.AsistenciaService;
import com.ironfit.ironfit.servicio.rutina.EjercicioService;
import com.ironfit.ironfit.servicio.seguridad.UsuarioService;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardAdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private AsistenciaService asistenciaService;

    // --- CLIENTES ACTIVOS ---
    @GetMapping("/clientes-activos/total")
    public long getTotalClientesActivos() {
        return usuarioService.contarClientesActivos();
    }

    // --- ENTRENADORES ACTIVOS ---
    @GetMapping("/entrenadores-activos/total")
    public long getTotalEntrenadoresActivos() {
        return usuarioService.contarEntrenadoresActivos();
    }

    // --- EJERCICIOS REGISTRADOS ---
    @GetMapping("/ejercicios/total")
    public long getTotalEjercicios() {
        return ejercicioService.contarEjercicios();
    }

    // --- AUDITORÍAS DEL MES (asistencias del mes) ---
    @GetMapping("/auditorias/mes")
    public long getTotalAuditoriasMes() {
        return asistenciaService.contarAuditoriasMesActual();
    }
}
