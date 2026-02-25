package com.ironfit.ironfit.dto;

import java.util.List;

public class UsuarioResumenDTO {
    private Long idUsuario;
    private String tipoDoc;
    private String nroDoc;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String email;
    private Boolean activo;
    private List<String> roles; // ROLE_ADMIN, ROLE_CLIENTE, etc.

    public UsuarioResumenDTO() {}

    public UsuarioResumenDTO(Long idUsuario,
                                String tipoDoc,
                                String nroDoc,
                                String nombres,
                                String apellidos,
                                String telefono,
                                String email,
                                Boolean activo,
                                List<String> roles) {
        this.idUsuario = idUsuario;
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.activo = activo;
        this.roles = roles;
    }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getTipoDoc() { return tipoDoc; }
    public void setTipoDoc(String tipoDoc) { this.tipoDoc = tipoDoc; }

    public String getNroDoc() { return nroDoc; }
    public void setNroDoc(String nroDoc) { this.nroDoc = nroDoc; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}
