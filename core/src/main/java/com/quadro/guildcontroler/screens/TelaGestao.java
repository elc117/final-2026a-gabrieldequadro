package com.quadro.guildcontroler.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.quadro.guildcontroler.MainGame;

public class TelaGestao implements Screen {
    private final MainGame game;

    public TelaGestao(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("Ecrã de Gestão da Guilda Iniciado!");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        // Os desenhos virão aqui!
        game.batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}