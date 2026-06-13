package com.quadro.guildcontroler.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.quadro.guildcontroler.MainGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TelaPlataforma implements Screen {
    private final MainGame game;
    private ShapeRenderer shapeRenderer;

    // Constantes do Cenário
    private final float ALTURA_CHAO = 100f;

    // Física e Posição do Jogador
    private Rectangle jogadorRect; // Caixa de colisão do jogador
    private float jogadorVelocidadeX = 300f;
    private float velocidadeY = 0f;
    private final float GRAVIDADE = -1200f;
    private final float FORCA_PULO = 550f;
    private boolean estaNoChao = true;


    private List<Rectangle> moedas;
    private Rectangle obstaculoPorta;

    public TelaPlataforma(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("Iniciando a Expedição Side-Scroller!");
        shapeRenderer = new ShapeRenderer();
        jogadorRect = new Rectangle(50, ALTURA_CHAO, 40, 40);
        moedas = new ArrayList<>();
        moedas.add(new Rectangle(300, ALTURA_CHAO + 20, 20, 20));
        moedas.add(new Rectangle(450, ALTURA_CHAO + 150, 20, 20));
        moedas.add(new Rectangle(600, ALTURA_CHAO + 20, 20, 20));
        obstaculoPorta = new Rectangle(500, ALTURA_CHAO, 50, 150);
    }

    @Override
    public void render(float delta) {

      // LÓGICA E FÍSICA

        float posicaoAntigaX = jogadorRect.x;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            jogadorRect.x -= jogadorVelocidadeX * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            jogadorRect.x += jogadorVelocidadeX * delta;
        }

        if (jogadorRect.x < 0) jogadorRect.x = 0;

        if (jogadorRect.overlaps(obstaculoPorta)) {
            jogadorRect.x = posicaoAntigaX;
        }

        if (!estaNoChao) {
            velocidadeY += GRAVIDADE * delta;
        }

        if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) && estaNoChao) {
            velocidadeY = FORCA_PULO;
            estaNoChao = false;
        }

        jogadorRect.y += velocidadeY * delta;

        if (jogadorRect.y <= ALTURA_CHAO) {
            jogadorRect.y = ALTURA_CHAO;
            velocidadeY = 0f;
            estaNoChao = true;
        }

        Iterator<Rectangle> iter = moedas.iterator();
        while (iter.hasNext()) {
            Rectangle moeda = iter.next();
            if (jogadorRect.overlaps(moeda)) {
                game.minhaGuilda.adicionarOuro(50);
                iter.remove();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new TelaGestao(game));
        }


        // DESENHAR OS ELEMENTOS NO ECRÃ
        Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0.2f, 0.8f, 0.2f, 1f);
        shapeRenderer.rect(0, 0, 1000, ALTURA_CHAO);
        shapeRenderer.setColor(1f, 0.8f, 0.1f, 1f);
        for (Rectangle moeda : moedas) {
            shapeRenderer.rect(moeda.x, moeda.y, moeda.width, moeda.height);
        }

        shapeRenderer.setColor(0.1f, 0.2f, 0.5f, 1f);
        shapeRenderer.rect(obstaculoPorta.x, obstaculoPorta.y, obstaculoPorta.width, obstaculoPorta.height);

        shapeRenderer.setColor(0.8f, 0.1f, 0.1f, 1f);
        shapeRenderer.rect(jogadorRect.x, jogadorRect.y, jogadorRect.width, jogadorRect.height);

        shapeRenderer.end();
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