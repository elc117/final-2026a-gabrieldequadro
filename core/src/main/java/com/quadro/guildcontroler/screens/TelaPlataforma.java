package com.quadro.guildcontroler.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.quadro.guildcontroler.MainGame;

public class TelaPlataforma implements Screen {
    private final MainGame game;
    private ShapeRenderer shapeRenderer;

    // provisório
    private float jogadorX = 50;  // Posição horizontal
    private float jogadorY = 100; // Posição vertical (exatamente em cima do chão)
    private float jogadorVelocidade = 300; // Velocidade de movimento (pixels por segundo)

    public TelaPlataforma(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("Iniciando a Expedição Side-Scroller!");
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        // 1. ATUALIZAR A LÓGICA (Movimentação)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            jogadorX -= jogadorVelocidade * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            jogadorX += jogadorVelocidade * delta;
        }


        if (jogadorX < 0) jogadorX = 0;


        Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeType.Filled);

        shapeRenderer.setColor(0.2f, 0.8f, 0.2f, 1f); // R, G, B, Alpha
        shapeRenderer.rect(0, 0, 1000, 100);
        shapeRenderer.setColor(0.8f, 0.1f, 0.1f, 1f);
        shapeRenderer.rect(jogadorX, jogadorY, 40, 40);

        shapeRenderer.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new TelaGestao(game));
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { dispose(); }
}