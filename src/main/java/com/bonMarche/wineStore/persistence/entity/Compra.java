package com.bonMarche.wineStore.persistence.entity;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Integer idCompra;

    @Column(name = "id_cliente")
    private String idCliente;
    private LocalDateTime fecha;

    @Column(name = "medio_pago")
    private String medioPago;
    private String comentario;
    private String estado;


    //Relaciones: antes mappedBy = "vino" ahora es compra porque es la relacion que da circularidad a esta relacion
    @OneToMany(mappedBy = "compra", cascade = {CascadeType.ALL})
    // viene de la clase comprasVino (private Vino vino) Para obtener una lista de los vinos de una compra,
    // El atributo cascade es p indicar q se van a guardar vinos en cascada(método save CompraRepository)
    // Quiere decir que todos los procesos que se hagan contra la base de datos de una compra van a incluir en CASCADA sus vinos.
    // puedo acceder a la tabla vino, a traves de la tabla comprasVino
    private List<ComprasVino> Vinos;


    @ManyToOne
    // se relaciona a la tabla categoría a traves del id_cliente,
    // insertable y updatable false quiere decir que através de esa relación no se va a insertar ni actualizar un cliente en la tabla cliente.
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private Cliente cliente;


    //Getters & Setters
    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<ComprasVino> getVinos() {
        return Vinos;
    }

    public void setVinos(List<ComprasVino> vinos) {
        Vinos = vinos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
