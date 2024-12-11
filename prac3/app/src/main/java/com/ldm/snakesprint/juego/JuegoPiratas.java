package com.ldm.snakesprint.juego;

import com.ldm.snakesprint.Pantalla;
import com.ldm.snakesprint.androidimpl.AndroidJuego;

public class JuegoPiratas extends AndroidJuego {
    @Override
    public Pantalla getStartScreen() {
        return new LoadingScreen(this);
    }
}
