package com.quadro.guildcontroler.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.quadro.guildcontroler.MainGame;
import com.quadro.guildcontroler.model.Expedicao;
import com.quadro.guildcontroler.model.Obstaculo;
import com.quadro.guildcontroler.model.MembroGuilda;
import com.quadro.guildcontroler.model.Guerreiro;
import com.quadro.guildcontroler.model.Mago;
import com.quadro.guildcontroler.model.Ladino;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TelaPlataforma implements Screen {
    private final MainGame game;
    private final Expedicao expedicao; // Recebe o grupo vindo da Taverna
    private ShapeRenderer shapeRenderer;
    private final float ALTURA_CHAO = 100f;
    private Rectangle jogadorRect;
    private float jogadorVelocidadeX = 300f;
    private float velocidadeY = 0f;
    private final float GRAVIDADE = -1200f;
    private final float FORCA_PULO = 550f;
    private boolean estaNoChao = true;
    private List<Rectangle> moedas;
    private Rectangle obstaculoVisual;
    private Obstaculo obstaculoLogico;
    private boolean portaDestruida = false;
    public TelaPlataforma(MainGame game, Expedicao expedicao) {
        this.game = game;
        this.expedicao = expedicao;
    }

    @Override
    public void show() {
        System.out.println("Expedição Iniciada com o grupo!");
        shapeRenderer = new ShapeRenderer();

        jogadorRect = new Rectangle(50, ALTURA_CHAO, 40, 40);

        moedas = new ArrayList<>();
        moedas.add(new Rectangle(300, ALTURA_CHAO + 20, 20, 20));
        moedas.add(new Rectangle(450, ALTURA_CHAO + 150, 20, 20));
        obstaculoVisual = new Rectangle(650, ALTURA_CHAO, 50, 140);
        obstaculoLogico = new Obstaculo("Portão de Ferro Pesado", "Força", 1, 200.0);
    }

    @Override
    public void render(float delta) {

        //INPUTS ESPECIAIS (TAB E INTERAÇÃO)


        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            expedicao.alternarPersonagem();
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && !portaDestruida) {
            if (jogadorRect.overlaps(obstaculoVisual)) {
                MembroGuilda heroiAtivo = expedicao.getPersonagemAtivo();
                if (obstaculoLogico.tentarSuperar(heroiAtivo)) {
                    portaDestruida = true; // Abre o caminho
                    game.minhaGuilda.adicionarOuro(obstaculoLogico.getRecompensaOculta());
                }
            }
        }

        //FÍSICA E MOVIMENTAÇÃO

        float posicaoAntigaX = jogadorRect.x;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            jogadorRect.x -= jogadorVelocidadeX * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            jogadorRect.x += jogadorVelocidadeX * delta;
        }

        if (jogadorRect.x < 0) jogadorRect.x = 0;

        if (!portaDestruida && jogadorRect.overlaps(obstaculoVisual)) {
            jogadorRect.x = posicaoAntigaX;
        }

        if (!estaNoChao) velocidadeY += GRAVIDADE * delta;
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


        // RENDERIZAÇÃO GRÁFICA

        Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0.2f, 0.8f, 0.2f, 1f);
        shapeRenderer.rect(0, 0, 1000, ALTURA_CHAO);
        shapeRenderer.setColor(1f, 0.8f, 0.1f, 1f);
        for (Rectangle moeda : moedas) {
            shapeRenderer.rect(moeda.x, moeda.y, moeda.width, moeda.height);
        }

        if (!portaDestruida) {
            shapeRenderer.setColor(0.1f, 0.2f, 0.5f, 1f);
            shapeRenderer.rect(obstaculoVisual.x, obstaculoVisual.y, obstaculoVisual.width, obstaculoVisual.height);
        }

        MembroGuilda ativo = expedicao.getPersonagemAtivo();
        if (ativo instanceof Guerreiro) {
            shapeRenderer.setColor(0.8f, 0.1f, 0.1f, 1f);
        } else if (ativo instanceof Mago) {
            shapeRenderer.setColor(0.6f, 0.1f, 0.8f, 1f);
        } else if (ativo instanceof Ladino) {
            shapeRenderer.setColor(0.1f, 0.6f, 0.1f, 1f);
        } else {
            shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1f);
        }
        shapeRenderer.rect(jogadorRect.x, jogadorRect.y, jogadorRect.width, jogadorRect.height);

        shapeRenderer.end();
    }

    @Override public void dispose() { shapeRenderer.dispose(); }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { dispose(); }
}