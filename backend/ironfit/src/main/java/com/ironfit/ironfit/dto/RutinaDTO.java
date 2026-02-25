package com.ironfit.ironfit.dto;

public class RutinaDTO {

    private Long idRutina;
    private String nombre;
    private String descripcion;
    private Short orden;

    public RutinaDTO(Long idRutina, String nombre, String descripcion, Short orden) {
        this.idRutina = idRutina;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.orden = orden;
    }

    public Long getIdRutina() { return idRutina; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Short getOrden() { return orden; }
}
