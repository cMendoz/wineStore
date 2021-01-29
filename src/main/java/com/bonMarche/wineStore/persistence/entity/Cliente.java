package com.bonMarche.wineStore.persistence.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    private String Id;
    private String nombre;
    private String apellidos;
    private Long celular;
    private String direccion;

    @Column(name = "correo_electronico")
    private String correoElectronico;


    //Relaciones:

    // si en Compra utilizamos manyToOne aqui utilizamos el contrario,
    // y le indicamos porque atributo esta relacionado con la tabla Compra (private Cliente cliente;)
    @OneToMany(mappedBy="cliente")
    private List<Compra> compras;


    //Getters & Setters:
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo_electronico() {
        return correoElectronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correoElectronico = correo_electronico;
    }
}
