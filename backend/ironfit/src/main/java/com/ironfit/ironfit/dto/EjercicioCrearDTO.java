package com.ironfit.ironfit.dto;

public class EjercicioCrearDTO {
    private String nombre;
    private String descripcion;
    private Long idCategoria;
    private Long idGrupoMuscular;
    private Integer seriesSugeridas;
    private Integer repeticionesSugeridas;
    private String tipoEquipo;
    
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
	public Long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	public Long getIdGrupoMuscular() {
		return idGrupoMuscular;
	}
	public void setIdGrupoMuscular(Long idGrupoMuscular) {
		this.idGrupoMuscular = idGrupoMuscular;
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
