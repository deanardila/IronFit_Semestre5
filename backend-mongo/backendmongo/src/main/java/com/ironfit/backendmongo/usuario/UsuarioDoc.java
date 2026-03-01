package com.ironfit.backendmongo.usuario;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "usuarios")
public class UsuarioDoc {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String passwordHash;

    private boolean activo = true;

    private List<String> roles; // ["ADMIN","ENTRENADOR","CLIENTE"]

    public UsuarioDoc() {}

    public UsuarioDoc(String email, String passwordHash, List<String> roles) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.roles = roles;
    }

    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isActivo() { return activo; }
    public List<String> getRoles() { return roles; }

    public void setId(String id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}