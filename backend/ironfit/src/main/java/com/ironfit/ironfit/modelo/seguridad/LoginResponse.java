package com.ironfit.ironfit.modelo.seguridad;

public class LoginResponse {

    private String token;
    private String rol;
    private String usuario;

    public LoginResponse(String token, String rol, String usuario) {
        this.token = token;
        this.rol = rol;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public String getRol() {
        return rol;
    }

    public String getUsuario() {
        return usuario;
    }
}

