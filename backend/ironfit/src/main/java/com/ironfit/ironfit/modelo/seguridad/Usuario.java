package com.ironfit.ironfit.modelo.seguridad;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "tipo_doc", length = 10, nullable = false)
    private String tipoDoc;

    @Column(name = "nro_doc", length = 30, nullable = false, unique = true)
    private String nroDoc;

    @Column(name = "nombres", length = 80, nullable = false)
    private String nombres;

    @Column(name = "apellidos", length = 80, nullable = false)
    private String apellidos;

    @Column(name = "telefono", length = 30)
    private String telefono;

    @Column(name = "email", length = 80)
    private String email;

    @Column(name = "usuario_login", length = 50, nullable = false, unique = true)
    private String usuarioLogin;

    @Column(name = "clave", nullable = false)
    private String clave;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "usuario")
    private List<UsuarioRol> roles;

    @OneToMany(mappedBy = "usuario")
    private List<UsuarioEspecialidad> especialidades;

    @OneToMany(mappedBy = "usuario")
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();

    // Constructor vacío
    public Usuario() {}

    // Getters y Setters

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<UsuarioRol> getRoles() {
        return roles;
    }

    public void setRoles(List<UsuarioRol> roles) {
        this.roles = roles;
    }

    public List<UsuarioEspecialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<UsuarioEspecialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public Set<UsuarioRol> getUsuarioRoles() {
    return usuarioRoles;
    }

    public void setUsuarioRoles(Set<UsuarioRol> usuarioRoles) {
        this.usuarioRoles = usuarioRoles;
    }
}
