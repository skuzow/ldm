package com.ldm.snakesprint.juego;

public class Fruta {
    public static final int TIPO_1 = 0;
    public static final int TIPO_2 = 1;
    public static final int TIPO_3 = 2;
    public int x, y;
    public int tipo;

    public Fruta(int x, int y, int tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
    }
}
