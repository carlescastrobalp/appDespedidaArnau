package com.carlescastro.despedidaarnau.modelo;

public class TablaDTO {

    public boolean preguntaOPrueba;

    public TablaDTO() { //Constructor vacio necesario para FireStore
    }

    public TablaDTO(boolean preguntaOPrueba) {
        this.preguntaOPrueba = preguntaOPrueba;
    }
}
