package com.ironfit.ironfit.modelo.evaluacion;

import com.ironfit.ironfit.modelo.seguridad.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "evaluacion_fisica")

public class EvaluacionFisica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_eval")
    private Long idEval;

    // Cliente evaluado
    @ManyToOne
    @JoinColumn(name = "id_usuario_cliente", nullable = false)
    private Usuario usuarioCliente;

    // Quien realiza la evaluación (entrenador, admin, etc.)
    @ManyToOne
    @JoinColumn(name = "id_usuario_evaluador", nullable = false)
    private Usuario usuarioEvaluador;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "peso_kg", precision = 4, scale = 2)
    private BigDecimal pesoKg;

    @Column(name = "talla_m", precision = 4, scale = 2)
    private BigDecimal tallaM;

    @Column(name = "imc", precision = 4, scale = 2)
    private BigDecimal imc;

    @Column(name = "grasa_pct", precision = 4, scale = 2)
    private BigDecimal grasaPct;

    @Column(name = "cintura_cm", precision = 5, scale = 1)
    private BigDecimal cinturaCm;

    @Column(name = "cadera_cm", precision = 5, scale = 1)
    private BigDecimal caderaCm;

    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;

    public EvaluacionFisica() {
    }

    public Long getIdEval() {
        return idEval;
    }

    public void setIdEval(Long idEval) {
        this.idEval = idEval;
    }

    public Usuario getUsuarioCliente() {
        return usuarioCliente;
    }

    public void setUsuarioCliente(Usuario usuarioCliente) {
        this.usuarioCliente = usuarioCliente;
    }

    public Usuario getUsuarioEvaluador() {
        return usuarioEvaluador;
    }

    public void setUsuarioEvaluador(Usuario usuarioEvaluador) {
        this.usuarioEvaluador = usuarioEvaluador;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(BigDecimal pesoKg) {
        this.pesoKg = pesoKg;
    }

    public BigDecimal getTallaM() {
        return tallaM;
    }

    public void setTallaM(BigDecimal tallaM) {
        this.tallaM = tallaM;
    }

    public BigDecimal getImc() {
        return imc;
    }

    public void setImc(BigDecimal imc) {
        this.imc = imc;
    }

    public BigDecimal getGrasaPct() {
        return grasaPct;
    }

    public void setGrasaPct(BigDecimal grasaPct) {
        this.grasaPct = grasaPct;
    }

    public BigDecimal getCinturaCm() {
        return cinturaCm;
    }

    public void setCinturaCm(BigDecimal cinturaCm) {
        this.cinturaCm = cinturaCm;
    }

    public BigDecimal getCaderaCm() {
        return caderaCm;
    }

    public void setCaderaCm(BigDecimal caderaCm) {
        this.caderaCm = caderaCm;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}

