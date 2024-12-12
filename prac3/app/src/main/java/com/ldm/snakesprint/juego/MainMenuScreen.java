package com.ldm.snakesprint.juego;

import java.util.List;
import com.ldm.snakesprint.Juego;
import com.ldm.snakesprint.Graficos;
import com.ldm.snakesprint.Input.TouchEvent;
import com.ldm.snakesprint.Pantalla;
import com.ldm.snakesprint.androidimpl.AndroidJuego;

public class MainMenuScreen extends Pantalla {
    public MainMenuScreen(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        Graficos g = juego.getGraphics();
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                // Toggle sound button
                if (inBounds(event, 0, g.getHeight() - 64, 64, 64)) {
                    Configuraciones.sonidoHabilitado = !Configuraciones.sonidoHabilitado;
                    if (Configuraciones.sonidoHabilitado) {
                        Assets.pulsar.play(1);
                        if (juego instanceof AndroidJuego) {
                            AndroidJuego androidJuego = (AndroidJuego) juego;
                            if (androidJuego.getThemeMusic() != null && !androidJuego.getThemeMusic().isPlaying()) {
                                androidJuego.getThemeMusic().play();
                            }
                        }
                    } else {
                        if (juego instanceof AndroidJuego) {
                            AndroidJuego androidJuego = (AndroidJuego) juego;
                            if (androidJuego.getThemeMusic() != null && androidJuego.getThemeMusic().isPlaying()) {
                                androidJuego.getThemeMusic().pause();
                            }
                        }
                    }
                }
                
                // Start game
                if (inBounds(event, 64, 220, 192, 42)) {
                    juego.setScreen(new PantallaJuego(juego));
                    if (Configuraciones.sonidoHabilitado) {
                        Assets.pulsar.play(1);
                    }
                    return;
                }

                // High scores screen
                if (inBounds(event, 64, 220 + 42, 192, 42)) {
                    juego.setScreen(new PantallaMaximasPuntuaciones(juego));
                    if (Configuraciones.sonidoHabilitado) {
                        Assets.pulsar.play(1);
                    }
                    return;
                }

                // Help screen
                if (inBounds(event, 64, 220 + 84, 192, 42)) {
                    juego.setScreen(new PantallaAyuda(juego));
                    if (Configuraciones.sonidoHabilitado) {
                        Assets.pulsar.play(1);
                    }
                    return;
                }
            }
        }
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        return event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1;
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();

        juego.setBackground();
        g.drawPixmap(Assets.logo, 32, 20);
        g.drawPixmap(Assets.menuprincipal, 64, 220);
        if(Configuraciones.sonidoHabilitado)
            g.drawPixmap(Assets.botones, 0, 416, 0, 0, 64, 64);
        else
            g.drawPixmap(Assets.botones, 0, 416, 64, 0, 64, 64);
    }

    @Override
    public void pause() {
        Configuraciones.save(juego.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

