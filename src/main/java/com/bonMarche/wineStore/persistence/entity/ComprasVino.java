package com.bonMarche.wineStore.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "compras_vinos")
public class ComprasVino {

    //Cuando tienen dos id hay que crear otra clase que contenga los id, en este caso ComprasVinoPK (PK=primary key)
    @EmbeddedId //se utiliza cuando la clave primaria es compuesta
    private ComprasVinoPK id; //traigo la clase ComprasVinoPk donde estan las claves primarias

    //los demás atributos:
    private Integer cantidad;
    private Double total;
    private Boolean estado;


    //Relaciones:
    @ManyToOne
    @MapsId("idCompra")//el id lo traigo de Compra. permite que cuando comprasVino vaya a guardar en cascada, sabrá a que clave primaria pertenece cada uno de los productos que están en una compra
    @JoinColumn(name = "id_compra", updatable = false, insertable = false)
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "id_vino", updatable = false, insertable = false)
    private Vino vino;


    //Getters & Setters
    public ComprasVinoPK getId() {
        return id;
    }

    public void setId(ComprasVinoPK id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Vino getVino() {
        return vino;
    }

    public void setVino(Vino vino) {
        this.vino = vino;
    }
}
