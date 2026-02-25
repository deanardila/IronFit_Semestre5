package com.ironfit.ironfit.dto;

public class EjercicioDTO {
    private Long idEjercicio;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String grupoMuscular;
    private Integer seriesSugeridas;
    private Integer repeticionesSugeridas;
    private String tipoEquipo;
    
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getGrupoMuscular() {
		return grupoMuscular;
	}
	public void setGrupoMuscular(String grupoMuscular) {
		this.grupoMuscular = grupoMuscular;
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

    
}
