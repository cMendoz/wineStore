package com.bonMarche.wineStore.domain.dto;

//Esta clase, y su par AuthenticationRequest son los DTO encargados de recibir y enviar
// la informacion necesaria para crear un JWT dentro de un controlador

public class AuthenticationRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
