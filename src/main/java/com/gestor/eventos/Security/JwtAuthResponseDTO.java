package com.gestor.eventos.Security;

public class JwtAuthResponseDTO {

    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";
    private String rol;
    private Long userId;
    private String nombreUsuario;

    public JwtAuthResponseDTO(String tokenDeAcceso, String rol, Long userId, String nombreUsuario) {
        this.tokenDeAcceso = tokenDeAcceso;
        this.rol = rol;
        this.userId = userId;
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTokenDeAcceso() {
        return tokenDeAcceso;
    }

    public void setTokenDeAcceso(String tokenDeAcceso) {
        this.tokenDeAcceso = tokenDeAcceso;
    }

    public String getTipoDeToken() {
        return tipoDeToken;
    }

    public void setTipoDeToken(String tipoDeToken) {
        this.tipoDeToken = tipoDeToken;
    }
}
