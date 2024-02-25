package com.alianzaRestFullstack.restfullstackjavaangular.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "shared_key")
    private String sharedKey;
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    @Column(unique = true, nullable = false)
    private String email;
    private String telefono;
    private String fecha;

    public Cliente() {
    }

    public Cliente(Integer id, String sharedKey, String nombreCompleto, String email, String telefono, String fecha) {
        this.id = id;
        this.sharedKey = sharedKey;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.telefono = telefono;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSharedKey() {
        return sharedKey;
    }

    public void setSharedKey(String sharedKey) {
        this.sharedKey = sharedKey;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}