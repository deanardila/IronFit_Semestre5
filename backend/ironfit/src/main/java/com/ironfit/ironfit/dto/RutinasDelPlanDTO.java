package com.ironfit.ironfit.dto;

public class RutinasDelPlanDTO {

    private Long idPlan;
    private String nombrePlan;
    private Boolean tieneRutinas;

    public RutinasDelPlanDTO(Long idPlan, String nombrePlan, 
                            Boolean tieneRutinas) {
        this.idPlan = idPlan;
        this.nombrePlan = nombrePlan;
        this.tieneRutinas = tieneRutinas;
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public Boolean getTieneRutinas() {
        return tieneRutinas;
    }

    public void setTieneRutinas(Boolean tieneRutinas) {
        this.tieneRutinas = tieneRutinas;
    }

}
