package com.ldm.snakesprint.juego;

import java.util.List;
import com.ldm.snakesprint.Juego;
import com.ldm.snakesprint.Graficos;
import com.ldm.snakesprint.Input.TouchEvent;
import com.ldm.snakesprint.Pantalla;

public class PantallaAyuda extends Pantalla {
    public PantallaAyuda(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 256 && event.y > 416 ) {
                    juego.setScreen(new PantallaAyuda2(juego));
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();
        juego.setBackground();
        g.drawPixmap(Assets.ayuda1, 64, 100);
        g.drawPixmap(Assets.botones, 256, 416, 68, 130, 64, 64);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}