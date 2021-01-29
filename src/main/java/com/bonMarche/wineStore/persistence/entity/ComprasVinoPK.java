package com.bonMarche.wineStore.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class ComprasVinoPK implements Serializable {

    //los ids:
    @Column(name = "id_compra")
    private Integer idCompra;

    @Column(name = "id_vino")
    private Integer idVino;

    //creo sus getters y setters:
    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Integer getIdVino() {
        return idVino;
    }

    public void setIdVino(Integer idVino) {
        this.idVino = idVino;
    }
}
