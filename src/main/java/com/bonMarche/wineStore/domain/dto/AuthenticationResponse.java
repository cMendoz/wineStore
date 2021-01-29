package com.bonMarche.wineStore.domain.dto;

//Esta clase, y su par AuthenticationRequest son los DTO encargados de recibir y enviar
// la informacion necesaria para crear un JWT dentro de un controlador

public class AuthenticationResponse {
    private String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
