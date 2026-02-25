package com.ironfit.ironfit.modelo.rutina;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ejercicio")
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejercicio")
    private Long idEjercicio;

    @Column(name = "nombre", length = 120, nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_grupo_muscular", nullable = false)
    private GrupoMuscular grupoMuscular;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @Column(name = "descripcion")
    private String descripcion;

    // 🔹 Nuevos campos del catálogo
    @Column(name = "series_sugeridas")
    private Integer seriesSugeridas;

    @Column(name = "repeticiones_sugeridas")
    private Integer repeticionesSugeridas;

    @Column(name = "tipo_equipo", length = 80)
    private String tipoEquipo;

    @OneToMany(mappedBy = "ejercicio")
    private List<RutinaEjercicio> rutinaEjercicios;

    @OneToMany(mappedBy = "ejercicio")
    private List<com.ironfit.ironfit.modelo.asistencia.AsistenciaEjercicio> asistenciasEjercicio;

    public Ejercicio() {
    }

    public Long getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(Long idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public GrupoMuscular getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(GrupoMuscular grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getSeriesSugeridas() {
        return seriesSugeridas;
    }

    public void setSeriesSugeridas(Integer seriesSugeridas) {
        this.seriesSugeridas = seriesSugeridas;
    }

    public Integer getRepeticionesSugeridas() {
        return repeticionesSugeridas;
    }

    public void setRepeticionesSugeridas(Integer repeticionesSugeridas) {
        this.repeticionesSugeridas = repeticionesSugeridas;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public List<RutinaEjercicio> getRutinaEjercicios() {
        return rutinaEjercicios;
    }

    public void setRutinaEjercicios(List<RutinaEjercicio> rutinaEjercicios) {
        this.rutinaEjercicios = rutinaEjercicios;
    }

    public List<com.ironfit.ironfit.modelo.asistencia.AsistenciaEjercicio> getAsistenciasEjercicio() {
        return asistenciasEjercicio;
    }

    public void setAsistenciasEjercicio(List<com.ironfit.ironfit.modelo.asistencia.AsistenciaEjercicio> asistenciasEjercicio) {
        this.asistenciasEjercicio = asistenciasEjercicio;
    }
}
