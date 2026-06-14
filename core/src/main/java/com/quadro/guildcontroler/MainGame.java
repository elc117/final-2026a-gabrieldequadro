package com.quadro.guildcontroler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.quadro.guildcontroler.model.Guilda;
import com.quadro.guildcontroler.screens.TelaGestao;

public class MainGame extends Game {
    public SpriteBatch batch;
    public Guilda minhaGuilda;
    public OrthographicCamera camera;
    public FitViewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();

        viewport = new FitViewport(1280, 720, camera);
        camera.position.set(1280 / 2f, 720 / 2f, 0);
        camera.update();

        minhaGuilda = new Guilda("Aventureiros do Quadro", 1000.0);
        this.setScreen(new TelaGestao(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}