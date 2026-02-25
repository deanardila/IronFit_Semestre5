package com.ironfit.ironfit.modelo.rutina;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "dia_semana")
public class DiaSemana {

    @Id
    @Column(name = "id_dia")
    private Short idDia;   // 1-7

    @Column(name = "nombre", length = 15, nullable = false)
    private String nombre; // Lunes, Martes...

    @Column(name = "abreviatura", length = 3, nullable = false)
    private String abreviatura; // LUN, MAR, etc.

    @OneToMany(mappedBy = "diaSemana")
    private List<Sesion> sesiones;

    public DiaSemana() {
    }

    public Short getIdDia() {
        return idDia;
    }

    public void setIdDia(Short idDia) {
        this.idDia = idDia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }
}