package com.ldm.snakesprint.juego;

import java.util.Random;

public class Mundo {
    static final int MUNDO_ANCHO = 10;
    static final int MUNDO_ALTO = 13;
    static final int INCREMENTO_PUNTUACION = 10;
    static final float TICK_INICIAL = 0.5f;
    static final float TICK_DECREMENTO = 0.05f;

    public Snake snake;
    public Fruta fruta;
    public boolean finalJuego = false;
    public int puntuacion = 0;

    boolean[][] campos = new boolean[MUNDO_ANCHO][MUNDO_ALTO];
    Random random = new Random();
    float tiempoTick = 0;
    static float tick = TICK_INICIAL;

    public Mundo() {
        snake = new Snake();
        colocarFruta();
    }

    private void colocarFruta() {
        for (int x = 0; x < MUNDO_ANCHO; x++) {
            for (int y = 0; y < MUNDO_ALTO; y++) {
                campos[x][y] = false;
            }
        }

        int len = snake.partes.size();
        for (int i = 0; i < len; i++) {
            Cuerpo parte = snake.partes.get(i);
            campos[parte.x][parte.y] = true;
        }

        int frutaX = random.nextInt(MUNDO_ANCHO);
        int frutaY = random.nextInt(MUNDO_ALTO);
        while (true) {
            if (!campos[frutaX][frutaY])
                break;
            frutaX += 1;
            if (frutaX >= MUNDO_ANCHO) {
                frutaX = 0;
                frutaY += 1;
                if (frutaY >= MUNDO_ALTO) {
                    frutaY = 0;
                }
            }
        }
        fruta = new Fruta(frutaX, frutaY, random.nextInt(3));
    }

    public void update(float deltaTime) {
        if (finalJuego)

            return;

        tiempoTick += deltaTime;

        while (tiempoTick > tick) {
            tiempoTick -= tick;
            snake.avance();
            if (snake.comprobarChoque()) {
                finalJuego = true;
                return;
            }

            Cuerpo cabeza = snake.partes.get(0);
            if (cabeza.x == fruta.x && cabeza.y == fruta.y) {
                puntuacion += INCREMENTO_PUNTUACION;
                snake.comer();
                if (snake.partes.size() == MUNDO_ANCHO * MUNDO_ALTO) {
                    finalJuego = true;
                    return;
                } else {
                    colocarFruta();
                }

                if (puntuacion % 100 == 0 && tick - TICK_DECREMENTO > 0) {
                    tick -= TICK_DECREMENTO;
                }
            }
        }
    }
}

