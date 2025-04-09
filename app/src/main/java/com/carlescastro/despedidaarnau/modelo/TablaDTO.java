package com.carlescastro.despedidaarnau.modelo;

public class TablaDTO {

    public boolean preguntaOPrueba;
    public int nivel;

    public TablaDTO() { //Constructor vacio necesario para FireStore
    }

    public TablaDTO(boolean preguntaOPrueba, int nivel) {
        this.preguntaOPrueba = preguntaOPrueba;
        this.nivel = nivel;
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
}
