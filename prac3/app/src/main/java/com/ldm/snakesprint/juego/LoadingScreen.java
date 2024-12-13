package com.ldm.snakesprint.juego;

import com.ldm.snakesprint.Juego;
import com.ldm.snakesprint.Graficos;
import com.ldm.snakesprint.Pantalla;
import com.ldm.snakesprint.Graficos.PixmapFormat;

public class LoadingScreen extends Pantalla{
    public LoadingScreen(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        Graficos g = juego.getGraphics();
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.menuprincipal = g.newPixmap("menuprincipal.png", PixmapFormat.ARGB4444);
        Assets.botones = g.newPixmap("botones.png", PixmapFormat.ARGB4444);
        Assets.ayuda1 = g.newPixmap("ayuda1.png", PixmapFormat.ARGB4444);
        Assets.ayuda2 = g.newPixmap("ayuda2.png", PixmapFormat.ARGB4444);
        Assets.ayuda3 = g.newPixmap("ayuda3.png", PixmapFormat.ARGB4444);
        Assets.numeros = g.newPixmap("numeros.png", PixmapFormat.ARGB4444);
        Assets.preparado = g.newPixmap("preparado.png", PixmapFormat.ARGB4444);
        Assets.menupausa = g.newPixmap("menupausa.png", PixmapFormat.ARGB4444);
        Assets.finjuego = g.newPixmap("finjuego.png", PixmapFormat.ARGB4444);
        Assets.snakearriba = g.newPixmap("snakearriba.png", PixmapFormat.ARGB4444);
        Assets.snakeizquierda = g.newPixmap("snakeizquierda.png", PixmapFormat.ARGB4444);
        Assets.snakeabajo = g.newPixmap("snakeabajo.png", PixmapFormat.ARGB4444);
        Assets.snakederecha = g.newPixmap("snakederecha.png", PixmapFormat.ARGB4444);
        Assets.cuerpo = g.newPixmap("cuerpo.png", PixmapFormat.ARGB4444);
        Assets.fruta1 = g.newPixmap("fruta1.png", PixmapFormat.ARGB4444);
        Assets.fruta2 = g.newPixmap("fruta2.png", PixmapFormat.ARGB4444);
        Assets.fruta3 = g.newPixmap("fruta3.png", PixmapFormat.ARGB4444);
        Assets.pulsar = juego.getAudio().nuevoSonido("pulsar.mp3");
        Assets.eat_sound = juego.getAudio().nuevoSonido("eat_sound.mp3");
        Assets.derrota = juego.getAudio().nuevoSonido("derrota.ogg");


        Configuraciones.cargar(juego.getFileIO());
        juego.setScreen(new MainMenuScreen(juego));
    }

    @Override
    public void present(float deltaTime) {

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