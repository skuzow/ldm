package com.ldm.snakesprint.juego;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    public static final int ARRIBA = 0;
    public static final int IZQUIERDA= 1;
    public static final int ABAJO = 2;
    public static final int DERECHA = 3;

    public List<Cuerpo> partes = new ArrayList<>();
    public int direccion;

    public Snake() {
        direccion = ARRIBA;
        partes.add(new Cuerpo(5, 6));
        partes.add(new Cuerpo(5, 7));
        partes.add(new Cuerpo(5, 8));
    }

    public void girarIzquierda() {
        direccion += 1;
        if(direccion > DERECHA)
            direccion = ARRIBA;
    }

    public void girarDerecha() {
        direccion -= 1;
        if(direccion < ARRIBA)
            direccion = DERECHA;
    }

    public void comer() {
        Cuerpo end = partes.get(partes.size()-1);
        partes.add(new Cuerpo(end.x, end.y));
    }

    public void avance() {
        Cuerpo cabeza = partes.get(0);

        int len = partes.size() - 1;
        for(int i = len; i > 0; i--) {
            Cuerpo antes = partes.get(i-1);
            Cuerpo parte = partes.get(i);
            parte.x = antes.x;
            parte.y = antes.y;
        }

        if(direccion == ARRIBA)
            cabeza.y -= 1;
        if(direccion == IZQUIERDA)
            cabeza.x -= 1;
        if(direccion == ABAJO)
            cabeza.y += 1;
        if(direccion == DERECHA)
            cabeza.x += 1;

        if(cabeza.x < 0)
            cabeza.x = 9;
        if(cabeza.x > 9)
            cabeza.x = 0;
        if(cabeza.y < 0)
            cabeza.y = 12;
        if(cabeza.y > 12)
            cabeza.y = 0;
    }

    public boolean comprobarChoque() {
        int len = partes.size();
        Cuerpo cabeza = partes.get(0);
        for(int i = 1; i < len; i++) {
            Cuerpo parte = partes.get(i);
            if(parte.x == cabeza.x && parte.y == cabeza.y)
                return true;
        }
        return false;
    }
}

