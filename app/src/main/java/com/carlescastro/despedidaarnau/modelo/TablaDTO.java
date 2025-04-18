package com.carlescastro.despedidaarnau.modelo;

public class TablaDTO {

    private String id;
    private boolean preguntaOPrueba;
    private int nivel;

    private String descripcion;

    private int estado;

    private String nombre;

    public TablaDTO() { //Constructor vacio necesario para FireStore
    }

    public TablaDTO(String id, boolean preguntaOPrueba, int nivel, String descripcion, String nombre) {
        this.id = id;
        this.preguntaOPrueba = preguntaOPrueba;
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.estado = 0;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPreguntaOPrueba() {
        return preguntaOPrueba;
    }

    public void setPreguntaOPrueba(boolean preguntaOPrueba) {
        this.preguntaOPrueba = preguntaOPrueba;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
