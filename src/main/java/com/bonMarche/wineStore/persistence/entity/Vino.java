package com.bonMarche.wineStore.persistence.entity;

import javax.persistence.*;

@Entity // esta anotación indica que esta clase mapea una tala de DB
@Table(name = "vinos") //hace referencia al nombre de la tabla de DB
public class Vino {

    @Id //Se agrega para indicar que es la clave primaria y es sencilla
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Se agrega para indicar que la clave prim se genera automáticamente, por tanto java tamién lo hará
    @Column(name = "id_vino") //siempre que una columna o clase se llame diferente a la DB hay que incluir la anotacion
    private Integer idVino;

    private String nombre;

    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(name = "codigo_barras")
    private String codigoBarras;

    @Column(name = "precio_venta")
    private Integer precioVenta;

    @Column(name = "cantidad_stock")
    private Integer cantidadStock;

    private Boolean estado;


    //Relaciones:
    @ManyToOne
    //se relaciona a la tabla categoría a traves del id_categoría,
    // insertable y updatable false quiere decir que através de esa relación no se va a insertar ni actualizar una  categoría en la tabla categoría.
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    private Categoria categoria;


    //Getters & Setters
    public Integer getIdVino() {
        return idVino;
    }

    public void setIdVino(Integer idVino) {
        this.idVino = idVino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Integer getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Integer precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(Integer cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
